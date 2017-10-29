package com.jlife.abon.facade

import com.jlife.abon.TransactionType
import com.jlife.abon.dto.billing.AccountData
import com.jlife.abon.dto.billing.ManualReplenishmentDetailsData
import com.jlife.abon.dto.billing.TransactionData
import com.jlife.abon.enumeration.PromotionType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.math.RoundingMode


/**
 * @author Dzmitry Misiuk
 */
class CalculateTariffWithPromotionTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var billingFacade: BillingFacade

    @Test
    fun test_hasPromotion_whenMoreThan3Month() {
        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID)
        val company = companyFacade.getCompany(DEFAULT_COMPANY_ID)

        val calculatedTariff = tariffFacade.calculateTariff(3, TARIFF_PAID_ID, DEFAULT_COMPANY_ID)

        var expectedTotalPrice = paidTariff.getMonthPrice(company.country).value
                .multiply(BigDecimal(3))
                .multiply(BigDecimal(0.95))
        expectedTotalPrice = expectedTotalPrice.setScale(2, RoundingMode.HALF_DOWN)

        var expectedPromotionValue = paidTariff.getMonthPrice(company.country).value
                .multiply(BigDecimal(3))
                .multiply(BigDecimal(0.05))
        expectedPromotionValue = expectedPromotionValue.setScale(2, RoundingMode.HALF_UP)

        assertThat(calculatedTariff.localTotalPrice.value)
                .isEqualByComparingTo(expectedTotalPrice)

        assertThat(calculatedTariff.appliedPromotions).isNotNull().hasSize(1)
        assertThat(calculatedTariff.appliedPromotionValue).isNotNull().isEqualTo(expectedPromotionValue)

        val tariffPromotionData = calculatedTariff.appliedPromotions[0]
        assertThat(tariffPromotionData.promotionValue).isEqualTo(BigDecimal(5))
        assertThat(tariffPromotionData.promotionType).isEqualTo(
                PromotionType.PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION)
    }

    @Test
    fun test_promotionIsSaved_whenPayWithPromotion() {
        val paidTariff = tariffFacade.getTariff(TARIFF_PAID_ID)
        val company = companyFacade.getCompany(DEFAULT_COMPANY_ID)
        val account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        doManualReplenishment(account, paidTariff.getMonthPrice(company.country).value.multiply(BigDecimal(10)));

        val initialBalance = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID).balance

        companyFacade.setCurrentTariffAsCompany(3, TARIFF_PAID_ID, DEFAULT_COMPANY_ID);

        val currentTariff = companyFacade.getCompany(DEFAULT_COMPANY_ID).currentTariff

        var expectedTotalPrice = paidTariff.getMonthPrice(company.country).value
                .multiply(BigDecimal(3))
                .multiply(BigDecimal(0.95))
        expectedTotalPrice = expectedTotalPrice.setScale(2, RoundingMode.HALF_DOWN)

        var expectedPromotionValue = paidTariff.getMonthPrice(company.country).value
                .multiply(BigDecimal(3))
                .multiply(BigDecimal(0.05))
        expectedPromotionValue = expectedPromotionValue.setScale(2, RoundingMode.HALF_UP)

        assertThat(currentTariff.localTotalPrice.value)
                .isEqualByComparingTo(expectedTotalPrice)

        assertThat(currentTariff.appliedPromotions).isNotNull().hasSize(1)
        assertThat(currentTariff.appliedPromotionValue).isNotNull().isEqualTo(expectedPromotionValue)

        val tariffPromotionData = currentTariff.appliedPromotions[0]
        assertThat(tariffPromotionData.promotionValue).isEqualTo(BigDecimal(5))
        assertThat(tariffPromotionData.promotionType).isEqualTo(
                PromotionType.PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION)

        val balanceAfterPayment = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID).balance
        val expectedBalanceAfterPayment = initialBalance.subtract(expectedTotalPrice)
        assertThat(balanceAfterPayment).isEqualTo(expectedBalanceAfterPayment)
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
}
