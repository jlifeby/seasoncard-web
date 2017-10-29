package com.jlife.abon.facade

import com.jlife.abon.criteria.AbonnementsClientCriteria
import com.jlife.abon.dto.AbonnementData
import com.jlife.abon.entity.Abonnement
import com.jlife.abon.enumeration.AbonnementsClientSearchMode
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired


/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
class AbonnementsClientCriteriaTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var clientFacade: ClientFacade

    @Autowired
    lateinit var enrollFacade: EnrollFacade

    @Test
    fun test_findAllClients_whenDefault() {
        val allClientsPage = clientFacade.findClients(emptyList(), DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        val clientsWithAbonnementsOrNotPage = clientFacade.findClients(
                listOf(AbonnementsClientCriteria()),
                DEFAULT_COMPANY_ID,
                MAX_PAGE_REQUEST)
        assertThat(allClientsPage.totalElements).isEqualTo(clientsWithAbonnementsOrNotPage.totalElements)
    }

    @Test
    fun test_findClientWithoutAbonnements() {
        val allClientsPage = clientFacade.findClients(emptyList(), DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)
        cleanCollections(Abonnement::class.java)
        val criteria = AbonnementsClientCriteria(AbonnementsClientSearchMode.HAS_NONE)
        val getClientWitActive = {
            clientFacade.findClients(
                    listOf(criteria),
                    DEFAULT_COMPANY_ID,
                    MAX_PAGE_REQUEST)
        }
        var clientWithActiveAbonPage = getClientWitActive()
        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(allClientsPage.totalElements)
    }

    @Test
    fun test_findClientWithActiveAbonnements() {
        cleanCollections(Abonnement::class.java)
        val criteria = AbonnementsClientCriteria(AbonnementsClientSearchMode.HAS_ACTIVE)
        val getClientWitActive = {
            clientFacade.findClients(
                    listOf(criteria),
                    DEFAULT_COMPANY_ID,
                    MAX_PAGE_REQUEST)
        }
        var clientWithActiveAbonPage = getClientWitActive()
        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(0)

        val enrollAbon = enrollAbon(DEFAULT_CARD_UUID)

        clientWithActiveAbonPage = getClientWitActive()

        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(1)

        enrollAbon.isActive = false
        abonnementFacade.save(enrollAbon)
        clientWithActiveAbonPage = getClientWitActive()

        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(0)
    }

    @Test
    fun test_findClientWithAnyAbonnements() {
        cleanCollections(Abonnement::class.java)
        val criteria = AbonnementsClientCriteria(AbonnementsClientSearchMode.HAS_ANY)
        val getClientWitActive = {
            clientFacade.findClients(
                    listOf(criteria),
                    DEFAULT_COMPANY_ID,
                    MAX_PAGE_REQUEST)
        }
        var clientWithActiveAbonPage = getClientWitActive()
        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(0)

        val enrollAbon = enrollAbon(DEFAULT_CARD_UUID)

        clientWithActiveAbonPage = getClientWitActive()

        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(1)

        enrollAbon.isActive = false
        abonnementFacade.save(enrollAbon)
        clientWithActiveAbonPage = getClientWitActive()

        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(1)
    }

    @Test
    fun test_findClientsOnlyWithNotActualAbonnements() {
        cleanCollections(Abonnement::class.java)
        val criteria = AbonnementsClientCriteria(AbonnementsClientSearchMode.HAS_ONLY_NOT_ACTIVE)
        val getClientWitActive = {
            clientFacade.findClients(
                    listOf(criteria),
                    DEFAULT_COMPANY_ID,
                    MAX_PAGE_REQUEST)
        }
        var clientWithActiveAbonPage = getClientWitActive()
        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(0)

        val enrollAbon = enrollAbon(DEFAULT_CARD_UUID)

        clientWithActiveAbonPage = getClientWitActive()

        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(0)

        enrollAbon.isActive = false
        abonnementFacade.save(enrollAbon)
        clientWithActiveAbonPage = getClientWitActive()

        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(1)
    }

    @Test
    fun test_findClientsWithActiveSpecificProduct() {
        cleanCollections(Abonnement::class.java)
        val criteria = AbonnementsClientCriteria(AbonnementsClientSearchMode.HAS_ACTIVE,
                DEFAULT_PRODUCT_ID_BY_TIME)
        val getClients = {
            clientFacade.findClients(
                    listOf(criteria),
                    DEFAULT_COMPANY_ID,
                    MAX_PAGE_REQUEST)
        }
        var clientWithActiveAbonPage = getClients()
        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(0)

        val enrollAbon = enrollAbon(DEFAULT_CARD_UUID)

        clientWithActiveAbonPage = getClients()

        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(1)

        enrollAbon.isActive = false
        abonnementFacade.save(enrollAbon)
        clientWithActiveAbonPage = getClients()
        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(0)
    }

    @Test
    fun test_findClientsWithSpecificProduct() {
        cleanCollections(Abonnement::class.java)
        val criteria = AbonnementsClientCriteria(AbonnementsClientSearchMode.ALL,
                DEFAULT_PRODUCT_ID_BY_TIME)
        val getClients = {
            clientFacade.findClients(
                    listOf(criteria),
                    DEFAULT_COMPANY_ID,
                    MAX_PAGE_REQUEST)
        }
        var clientWithActiveAbonPage = getClients()
        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(0)

        val enrollAbon = enrollAbon(DEFAULT_CARD_UUID)

        clientWithActiveAbonPage = getClients()

        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(1)

        clientWithActiveAbonPage = getClients()
        enrollAbon(ANOTHER_CARD_UUID, DEFAULT_PRODUCT_ID_BY_UNIT)
        assertThat(clientWithActiveAbonPage.totalElements).isEqualTo(1)
    }

    fun enrollAbon(cardUUID: Long, productId: String = DEFAULT_PRODUCT_ID_BY_TIME): AbonnementData {
        val sourceAbon = AbonnementData()
        sourceAbon.productId = productId
        sourceAbon.startDate = DateTime()
        sourceAbon.endDate = DateTime().plusMonths(1)
        sourceAbon.initialCountOfUnits = 0
        sourceAbon.initialCountOfUnits = 60
        sourceAbon.productPrice = 55.0
        sourceAbon.addComment("bla-bla-junit")
        val abonnement = enrollFacade.enrollAbonnement(cardUUID, ServiceBaseTestCase.DEFAULT_COMPANY_ID, sourceAbon)
        return abonnement
    }
}
