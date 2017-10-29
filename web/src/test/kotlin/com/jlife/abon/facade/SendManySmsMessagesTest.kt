package com.jlife.abon.facade

import com.jlife.abon.entity.Transaction
import com.jlife.abon.service.SCConfigurationService
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal

class SendManySmsMessagesTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var billingFacade: BillingFacade

    @Autowired
    lateinit var companySmsFacade: CompanySmsFacade

    @Autowired
    lateinit var scConfigurationService: SCConfigurationService

    @Test
    fun test_billForTwoMessages_whenTwoMessagesWereSent() {
        val company = companyFacade.getCompany(DEFAULT_COMPANY_ID)
        val account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        billingFacade.doManualReplenishment(account, BigDecimal(20))
        val cardUUIDs = arrayListOf(DEFAULT_CARD_UUID, ANOTHER_CARD_UUID)
        val allElemsPage = PageRequest(0, Integer.MAX_VALUE)
        val transactionsBefore = billingFacade.findTransactions(DEFAULT_COMPANY_ID, allElemsPage)
        val numberOfTransactionsBefore = transactionsBefore.totalElements
        val balanceBefore = getCompanyBalance()
        companySmsFacade.sendSmsMessage(cardUUIDs, "test message", DEFAULT_COMPANY_ID)
        val transactionsAfter = billingFacade.findTransactions(DEFAULT_COMPANY_ID, allElemsPage)
        val numberOfTransactionsAfter = transactionsAfter.totalElements
        val balanceAfter = getCompanyBalance()
        val smsPrice = scConfigurationService.getSmsPrice(company.country).value

        Assertions.assertThat(numberOfTransactionsAfter)
                .isEqualByComparingTo(numberOfTransactionsBefore + cardUUIDs.size)
        Assertions.assertThat(balanceAfter)
                .isEqualByComparingTo(balanceBefore - smsPrice * BigDecimal(cardUUIDs.size))
    }

    private fun getCompanyBalance(): BigDecimal {
        return billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID).balance
    }

    @Test
    fun test_billForTwoMessageTwice_whenMessageMoreThan70Symbols() {
        val company = companyFacade.getCompany(DEFAULT_COMPANY_ID)
        val account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        billingFacade.doManualReplenishment(account, BigDecimal(20))
        cleanCollections(Transaction::class.java)

        val cardUUIDs = arrayListOf(DEFAULT_CARD_UUID, ANOTHER_CARD_UUID)
        val balanceBefore = getCompanyBalance()
        var textMsg = RandomStringUtils.random(75);
        companySmsFacade.sendSmsMessage(cardUUIDs, textMsg, DEFAULT_COMPANY_ID)
        val transactionsAfter = billingFacade.findTransactions(DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        val numberOfTransactionsAfter = transactionsAfter.totalElements
        val balanceAfter = getCompanyBalance()
        val smsPrice = scConfigurationService.getSmsPrice(company.country).value

        assertThat(numberOfTransactionsAfter)
                .isEqualByComparingTo(2)

        assertThat(balanceAfter)
                .isEqualByComparingTo(balanceBefore - smsPrice * BigDecimal(cardUUIDs.size) * BigDecimal(2))

        val transactionData = transactionsAfter.content[0]
        assertThat(transactionData.value).isEqualTo(smsPrice * BigDecimal(2))
    }

    @Test
    fun test_billFoOneMessageTwice_whenMessageMoreThan70Symbols() {
        val company = companyFacade.getCompany(DEFAULT_COMPANY_ID)
        val account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        billingFacade.doManualReplenishment(account, BigDecimal(20))
        cleanCollections(Transaction::class.java)

        val balanceBefore = getCompanyBalance()
        var textMsg = RandomStringUtils.random(75);
        companySmsFacade.sendSmsMessage(DEFAULT_CARD_UUID, textMsg, DEFAULT_COMPANY_ID)
        val transactionsAfter = billingFacade.findTransactions(DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        val numberOfTransactionsAfter = transactionsAfter.totalElements
        val balanceAfter = getCompanyBalance()
        val smsPrice = scConfigurationService.getSmsPrice(company.country).value

        assertThat(numberOfTransactionsAfter)
                .isEqualByComparingTo(1)

        assertThat(balanceAfter)
                .isEqualByComparingTo(balanceBefore - smsPrice * BigDecimal(2))

        val transactionData = transactionsAfter.content[0]
        assertThat(transactionData.value).isEqualTo(smsPrice * BigDecimal(2))
    }
}
