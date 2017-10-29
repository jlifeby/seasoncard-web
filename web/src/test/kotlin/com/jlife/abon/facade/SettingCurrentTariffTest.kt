package com.jlife.abon.facade

import com.jlife.abon.TransactionType
import com.jlife.abon.dto.PriceData
import com.jlife.abon.dto.billing.AccountData
import com.jlife.abon.dto.billing.ManualReplenishmentDetailsData
import com.jlife.abon.dto.billing.TariffPaymentDetailsData
import com.jlife.abon.dto.billing.TransactionData
import com.jlife.abon.entity.Transaction
import com.jlife.abon.error.NotAllowedException
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.joda.time.Duration
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


/**
 * @author Dzmitry Misiuk
 */
class SettingCurrentTariffTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var billingFacade: BillingFacade;

    @Test
    fun test_checkTariffFields_whenSetCurrentLocalized() {
        val tsTestStart = DateTime.now()

        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        val companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        var account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        var valueOfPaidTariff = paidTariff.getMonthPrice(companyData.country).value
        doManualReplenishment(account, valueOfPaidTariff + valueOfPaidTariff)

        val nowPlusMonth = tsTestStart.plusMonths(1)
        val description = "description"

        paidTariff.startDate = tsTestStart
        paidTariff.endDate = nowPlusMonth
        paidTariff.comment = description
        val localTotalPrice = paidTariff.getMonthPrice(companyData.country)
        val totalPrice = localTotalPrice.value
        paidTariff.localTotalPrice = localTotalPrice

        val company = tariffFacade
                .setCurrentTariff(paidTariff, ServiceBaseTestCase.DEFAULT_COMPANY_ID)

        val currentTariff = companyFacade.getCompany(ServiceBaseTestCase.DEFAULT_COMPANY_ID).currentTariff

        assertNotNull(currentTariff)
        assertEquals(ServiceBaseTestCase.TARIFF_PAID_ID, currentTariff.id)

        assertThat(currentTariff.startDate).isBetween(tsTestStart, DateTime.now())

        assertEquals(nowPlusMonth, currentTariff.endDate)
        assertEquals(description, company.currentTariff.comment)

        assertThat(currentTariff.localTotalPrice.currency).isEqualTo(company.currency)

        assertThat(currentTariff.localTotalPrice.value).isEqualTo(totalPrice)
    }


    @Test
    fun test_checkUpdatingAccount_whenSetCurrentLocalized() {
        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        val companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);


        var account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        var valueOfPaidTariff = paidTariff.getMonthPrice(companyData.country).value
        doManualReplenishment(account, valueOfPaidTariff + valueOfPaidTariff)
        account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        val initialBalance = account.balance

        val now = DateTime.now();
        val nowPlusMonth = now.plusMonths(1);
        val description = "description";

        paidTariff.apply {
            startDate = now
            endDate = nowPlusMonth
            comment = description
            localTotalPrice = getMonthPrice(companyData.country)
        }

        var company = tariffFacade.setCurrentTariff(paidTariff, DEFAULT_COMPANY_ID);
        var currentTariff = companyFacade.getCompany(DEFAULT_COMPANY_ID).currentTariff;

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
        doManualReplenishment(account, valueOfPaidTariff + valueOfPaidTariff)
        account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        val initialBalance = account.balance

        val now = DateTime.now();
        val nowPlusMonth = now.plusMonths(1);
        val description = "description";

        paidTariff.apply {
            startDate = now
            endDate = nowPlusMonth
            comment = description
            localTotalPrice = getMonthPrice(companyData.country)
        }

        cleanCollections(Transaction::class.java)

        var company = tariffFacade.setCurrentTariff(paidTariff, DEFAULT_COMPANY_ID);

        val transactions = billingFacade.findTransactions(DEFAULT_COMPANY_ID, PageRequest(0, 10)).content
        assertThat(transactions).isNotNull().hasSize(1)
        val transactionData = transactions[0]
        assertThat(transactionData.accountId).isEqualTo(account.id)
        assertThat(transactionData.type).isEqualTo(TransactionType.PAYMENT)
        assertThat(transactionData.value).isEqualTo(paidTariff.getMonthPrice(companyData.country).value)

        assertThat(transactionData.details).isNotNull().isOfAnyClassIn(TariffPaymentDetailsData::class.java)
    }

    @Test
    fun test_checkCustomLocalTotalPrice_WhenTotalPriceNotNull() {
        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        val companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        var account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        var valueOfPaidTariff = paidTariff.getMonthPrice(companyData.country).value
        doManualReplenishment(account, valueOfPaidTariff + valueOfPaidTariff)

        val now = DateTime.now()
        val minusMonth = now.minusMonths(1)
        val nowPlusMonth = now.plusMonths(1)
        val description = "description"

        paidTariff.startDate = minusMonth
        paidTariff.endDate = nowPlusMonth
        paidTariff.comment = description
        val localTotalPrice = paidTariff.getMonthPrice(companyData.country)
        val totalPriceValue = localTotalPrice.value + BigDecimal(1)
        localTotalPrice.value = totalPriceValue
        paidTariff.localTotalPrice = localTotalPrice

        val company = tariffFacade
                .setCurrentTariff(paidTariff, ServiceBaseTestCase.DEFAULT_COMPANY_ID)

        val currentTariff = companyFacade.getCompany(ServiceBaseTestCase.DEFAULT_COMPANY_ID).currentTariff

        assertNotNull(currentTariff)
        assertThat(currentTariff.localTotalPrice.value).isEqualTo(totalPriceValue)
    }

    @Test
    fun test_setCurrentTariff_WhenNotEnoughMoney() {
        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        val companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        var account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        var valueOfPaidTariff = paidTariff.getMonthPrice(companyData.country).value

        val now = DateTime.now()
        val nowPlusMonth = now.plusMonths(1)
        val description = "description"

        paidTariff.startDate = now
        paidTariff.endDate = nowPlusMonth
        paidTariff.comment = description
        paidTariff.localTotalPrice = PriceData(companyData.currency, account.balance + valueOfPaidTariff)

        var e: Exception? = null
        try {
            val company = tariffFacade
                    .setCurrentTariff(paidTariff, ServiceBaseTestCase.DEFAULT_COMPANY_ID)
        } catch (ex: Exception) {
            e = ex
        }
        assertThat(e).isNotNull().isOfAnyClassIn(NotAllowedException::class.java)
    }

    private fun doManualReplenishment(account: AccountData, value: BigDecimal) {
        val transactionData = TransactionData()
        transactionData.apply {
            currency = account.currency
            this.value = value
            accountId = account.id
            type = TransactionType.REPLENISHMENT
        }

        val details = ManualReplenishmentDetailsData()
        details.comment = "based on ..."

        transactionData.details = details

        billingFacade.doReplenishment(transactionData)
    }

    @Test
    fun test_preparedTariff_whenAllDataCorrect(){
        val startingTestTime = DateTime.now()

        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID)
        val company = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        val calculatedTariff = tariffFacade.calculateTariff(2, TARIFF_PAID_ID, DEFAULT_COMPANY_ID)

        assertThat(calculatedTariff).isNotNull()
        assertThat(calculatedTariff.startDate).isBetween(startingTestTime, DateTime.now())
        assertThat(calculatedTariff.endDate).isBetween(startingTestTime.plusMonths(2), DateTime.now().plusMonths(2))
        assertThat(calculatedTariff.id).isEqualTo(paidTariff.id)
        assertThat(calculatedTariff.getMonthPrice(company.country)).isEqualTo(calculatedTariff.getMonthPrice(company.country))
        val monthPriceOfTariff = paidTariff.getMonthPrice(company.country).value
        assertThat(calculatedTariff.localTotalPrice.value).isEqualTo(monthPriceOfTariff + monthPriceOfTariff )
    }

    @Test
    fun test_preparedPlusDays_WhenLastTariffNotEnded(){
        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        val companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        var account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        var valueOfPaidTariff = paidTariff.getMonthPrice(companyData.country).value
        doManualReplenishment(account, valueOfPaidTariff + valueOfPaidTariff)

        val now = DateTime.now()
        val nowPlusMonth = now.plusMonths(1)
        val description = "description"

        paidTariff.startDate = now
        paidTariff.endDate = nowPlusMonth
        paidTariff.comment = description
        val localTotalPrice = paidTariff.getMonthPrice(companyData.country)
        paidTariff.localTotalPrice = localTotalPrice

        tariffFacade.setCurrentTariff(paidTariff, ServiceBaseTestCase.DEFAULT_COMPANY_ID)

        val currentTariff = companyFacade.getCompany(ServiceBaseTestCase.DEFAULT_COMPANY_ID).currentTariff

        val startingTestTime = DateTime.now()

        val company = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        val calculatedTariff = tariffFacade.calculateTariff(2, TARIFF_PAID_ID, DEFAULT_COMPANY_ID)

        val monthPriceOfTariff = paidTariff.getMonthPrice(company.country).value

        assertThat(calculatedTariff).isNotNull()
        assertThat(calculatedTariff.startDate).isBetween(startingTestTime, DateTime.now())

        val expectedEndDate = calculatedTariff.startDate.plusMonths(2).plus(Duration(calculatedTariff.startDate, currentTariff.endDate))
        assertThat(calculatedTariff.endDate).isEqualTo(expectedEndDate)

        assertThat(calculatedTariff.localTotalPrice.value).isEqualTo(monthPriceOfTariff + monthPriceOfTariff )
    }
}
