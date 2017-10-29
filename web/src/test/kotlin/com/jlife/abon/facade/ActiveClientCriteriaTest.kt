package com.jlife.abon.facade

import com.jlife.abon.criteria.ActiveClientCriteria
import com.jlife.abon.enumeration.ActiveClientSearchMode
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
class ActiveClientCriteriaTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var clientFacade: ClientFacade

    @Test
    fun test_findAllClients_whenDefault() {
        val allClientsPage = clientFacade.findClients(emptyList(), DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        val activeAndNoActiveClientPage = clientFacade.findClients(
                listOf(ActiveClientCriteria()),
                DEFAULT_COMPANY_ID,
                MAX_PAGE_REQUEST)
        assertThat(allClientsPage.totalElements).isEqualTo(activeAndNoActiveClientPage.totalElements)
    }

    @Test
    fun test_findActiveClients() {
        val allClientsPage = clientFacade.findClients(emptyList(), DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        companyFacade.archiveClient(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID)
        val activeClientsPage = clientFacade.findClients(
                listOf(ActiveClientCriteria(ActiveClientSearchMode.ONLY_ACTIVE)),
                DEFAULT_COMPANY_ID,
                MAX_PAGE_REQUEST)
        assertThat(allClientsPage.totalElements - 1).isEqualTo(activeClientsPage.totalElements)
    }

    @Test
    fun test_findNotActiveClients() {
        companyFacade.archiveClient(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID)
        val notActiveClientsPage = clientFacade.findClients(
                listOf(ActiveClientCriteria(ActiveClientSearchMode.ONLY_NOT_ACTIVE)),
                DEFAULT_COMPANY_ID,
                MAX_PAGE_REQUEST)
        assertThat(notActiveClientsPage.totalElements).isEqualTo(1)
    }
}
