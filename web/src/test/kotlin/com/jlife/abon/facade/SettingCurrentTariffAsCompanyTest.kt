package com.jlife.abon.facade

import com.jlife.abon.TransactionType
import com.jlife.abon.dto.billing.TariffPaymentDetailsData
import com.jlife.abon.entity.Transaction
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


/**
 * @author Dzmitry Misiuk
 */
class SettingCurrentTariffAsCompanyTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var billingFacade: BillingFacade;

    @Test
    fun test_checkTariffFields_whenSetCurrentLocalized() {
        val tsTestStart = DateTime.now()

        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        val companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        var account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        var valueOfPaidTariff = paidTariff.getMonthPrice(companyData.country).value
        billingFacade.doManualReplenishment(account, valueOfPaidTariff + valueOfPaidTariff)

        val calculatedTariff = tariffFacade.calculateTariff(1, TARIFF_PAID_ID, DEFAULT_COMPANY_ID)

        val company = companyFacade.setCurrentTariffAsCompany(1, TARIFF_PAID_ID, DEFAULT_COMPANY_ID)

        val currentTariff = companyFacade.getCompany(ServiceBaseTestCase.DEFAULT_COMPANY_ID).currentTariff

        assertNotNull(currentTariff)
        assertEquals(ServiceBaseTestCase.TARIFF_PAID_ID, currentTariff.id)

        assertThat(currentTariff.startDate).isBetween(tsTestStart, DateTime.now())
        assertThat(currentTariff.endDate).isBetween(tsTestStart.plusMonths(1), DateTime.now().plusMonths(1))
        assertThat(currentTariff.localTotalPrice.currency).isEqualTo(company.currency)
        assertThat(currentTariff.localTotalPrice.value).isEqualTo(calculatedTariff.localTotalPrice.value)
    }

    @Test
    fun test_checkUpdatingAccount_whenSetCurrentLocalized() {
        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        val companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        var account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        var valueOfPaidTariff = paidTariff.getMonthPrice(companyData.country).value
        billingFacade.doManualReplenishment(account, valueOfPaidTariff + valueOfPaidTariff)
        account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        val initialBalance = account.balance

        companyFacade.setCurrentTariffAsCompany(1, TARIFF_PAID_ID, DEFAULT_COMPANY_ID);

        account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        val balanceAfterSettingTariff = account.balance

        var expectedValueAfterSettingTariff = initialBalance.subtract(valueOfPaidTariff)
        assertThat(balanceAfterSettingTariff).isEqualByComparingTo(expectedValueAfterSettingTariff)
    }

    @Test
    fun test_checkCreatingTransaction_whenSetCurrentLocalized() {
        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        val companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);


        var account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        val valueOfPaidTariff = paidTariff.getMonthPrice(companyData.country).value
        billingFacade.doManualReplenishment(account, valueOfPaidTariff + valueOfPaidTariff)
        account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);

        cleanCollections(Transaction::class.java)

        companyFacade.setCurrentTariffAsCompany(1, TARIFF_PAID_ID, DEFAULT_COMPANY_ID);

        val transactions = billingFacade.findTransactions(DEFAULT_COMPANY_ID, PageRequest(0, 10)).content

        assertThat(transactions).isNotNull().hasSize(1)
        val transactionData = transactions[0]
        assertThat(transactionData.accountId).isEqualTo(account.id)
        assertThat(transactionData.type).isEqualTo(TransactionType.PAYMENT)
        assertThat(transactionData.value).isEqualTo(paidTariff.getMonthPrice(companyData.country).value)

        assertThat(transactionData.details).isNotNull().isOfAnyClassIn(TariffPaymentDetailsData::class.java)
    }


}
