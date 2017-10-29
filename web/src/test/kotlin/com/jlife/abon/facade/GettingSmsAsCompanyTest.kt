package com.jlife.abon.facade

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class GettingSmsAsCompanyTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var billingFacade: BillingFacade

    @Autowired
    lateinit var companySmsFacade: CompanySmsFacade

    @Test
    fun test_hasNoSms_whenCompanyHasNotSentYet() {
        val companySmses = companySmsFacade.findSmsMessagesForCompany(DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        assertThat(companySmses).isNotNull()
        assertThat(companySmses.content).isNotNull().hasSize(0)
    }

    @Test
    fun test_hasNoSmsForClient_whenCompanyHasNotSentYet() {
        val companySmses = companySmsFacade.findMessagesForClient(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        assertThat(companySmses).isNotNull()
        assertThat(companySmses.content).isNotNull().hasSize(0)
    }

    @Test
    fun test_gettingAllSmsAsCompany_WhenHaveForDifferentUsers() {
        val account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        billingFacade.doManualReplenishment(account, BigDecimal.TEN)
        companySmsFacade.sendSmsMessage(listOf(DEFAULT_CARD_UUID, ANOTHER_CARD_UUID),
                "junit sms", DEFAULT_COMPANY_ID);

        val companySmses = companySmsFacade.findSmsMessagesForCompany(DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        assertThat(companySmses).isNotNull()
        val smsMessages = companySmses.content
        assertThat(smsMessages).isNotNull().hasSize(2)
        val userIds = smsMessages.map { it.userId }

        assertThat(userIds).contains(DEFAULT_USER_ID)
        assertThat(userIds).contains(ANOTHER_USER_ID)
    }

    @Test
    fun test_gettingSmsAsCompanyForSpecificClient_WhenHaveForDifferentUsers() {
        val account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID)
        billingFacade.doManualReplenishment(account, BigDecimal.TEN)
        companySmsFacade.sendSmsMessage(DEFAULT_CARD_UUID,
                "junit sms", DEFAULT_COMPANY_ID);

        val companySmses = companySmsFacade.findSmsMessagesForCompany(DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        val smsMessages = companySmses.content
        assertThat(smsMessages).isNotNull().hasSize(1)
        val userIds = smsMessages.map { it.userId }

        assertThat(userIds).contains(DEFAULT_USER_ID)
    }
}