package com.jlife.abon.facade

import com.jlife.abon.criteria.PotentialClientCriteria
import com.jlife.abon.enumeration.PotentialClientSearchMode
import org.assertj.core.api.Assertions
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class PotentialClientCriteriaTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var clientFacade: ClientFacade

    @Test
    fun test_findAllClients_whenBoth() {
        val allClientsPage = clientFacade.findClients(emptyList(), DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        val activeAndNoActiveClientPage = clientFacade.findClients(
                listOf(PotentialClientCriteria(PotentialClientSearchMode.BOTH)),
                DEFAULT_COMPANY_ID,
                MAX_PAGE_REQUEST)
        Assertions.assertThat(allClientsPage.totalElements).isEqualTo(activeAndNoActiveClientPage.totalElements)
    }

    @Test
    fun test_findRealClients_whenDefault() {
        val allClientsPage = clientFacade.findClients(emptyList(), DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        val activeAndNoActiveClientPage = clientFacade.findClients(
                listOf(PotentialClientCriteria()),
                DEFAULT_COMPANY_ID,
                MAX_PAGE_REQUEST)
        Assertions.assertThat(allClientsPage.totalElements - 1).isEqualTo(activeAndNoActiveClientPage.totalElements)
    }

    @Test
    fun test_findRealClients_whenReal() {
        val allClientsPage = clientFacade.findClients(emptyList(), DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        companyFacade.archiveClient(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID)
        val activeClientsPage = clientFacade.findClients(
                listOf(PotentialClientCriteria(PotentialClientSearchMode.REAL)),
                DEFAULT_COMPANY_ID,
                MAX_PAGE_REQUEST)
        Assertions.assertThat(allClientsPage.totalElements - 1).isEqualTo(activeClientsPage.totalElements)
    }

    @Test
    fun test_findPotentialClients() {
        companyFacade.archiveClient(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID)
        val notActiveClientsPage = clientFacade.findClients(
                listOf(PotentialClientCriteria(PotentialClientSearchMode.POTENTIAL)),
                DEFAULT_COMPANY_ID,
                MAX_PAGE_REQUEST)
        Assertions.assertThat(notActiveClientsPage.totalElements).isEqualTo(1)
    }
}