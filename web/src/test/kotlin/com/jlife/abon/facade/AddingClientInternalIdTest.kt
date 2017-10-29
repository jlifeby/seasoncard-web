package com.jlife.abon.facade

import com.jlife.abon.dto.CardData
import com.jlife.abon.dto.ClientData
import com.jlife.abon.error.ApiErrorCode
import com.jlife.abon.error.NotAllowedException
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 * @author Dzmitry Misiuk
 */
class AddingClientInternalIdTest : ServiceBaseTestCase() {

    @Test
    fun test_internalIdInitialIsNull_whenNotSet() {
        val clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)

        assertThat(clientCard).isNotNull()
        val client = clientCard.client
        assertThat(client).isNotNull()
        assertThat(client.internalId).isNull()
    }

    @Test
    fun test_internalIdIsSet_whenUpdatingClient() {
        val clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(clientCard).isNotNull()
        val client = clientCard.client
        assertThat(client).isNotNull()
        assertThat(client.internalId).isNull()
        client.internalId = DEFAULT_CLIENT_INTERNAL_ID
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, client, DEFAULT_COMPANY_ID)

        val updatedClientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(updatedClientCard).isNotNull()
        val updatedClient = updatedClientCard.client
        assertThat(updatedClient).isNotNull()
        assertThat(updatedClient.internalId).isEqualTo(DEFAULT_CLIENT_INTERNAL_ID)
    }

    @Test
    fun test_internalIdIsCreated_whenCreateClient() {
        val myCardUUID = 90L
        val myInternalId = "my-internal-id"

        addCardToCompanyPool(myCardUUID, DEFAULT_COMPANY_ID)
        val clientData = ClientData()
        clientData.internalId = myInternalId
        clientData.cardUUID = myCardUUID
        clientData.name = "dd"
        clientData.lastName = "ll"
        clientData.phone = "375290000000"
        clientData.birthday = DateTime.now().minusYears(30)
        val createdClientData = companyFacade.createClient(clientData, DEFAULT_COMPANY_ID)

        val storedCardData = cardFacade.getCardWithClient(myCardUUID, DEFAULT_COMPANY_ID)

        assertThat(storedCardData.id).isNotNull()
        val storedClient = storedCardData.client
        assertThat(storedClient.internalId).isEqualTo(myInternalId)
    }

    @Test
    fun test_NotAllowingCreateClientWithTheSameInternalId(){
        val clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(clientCard).isNotNull()
        val client = clientCard.client
        assertThat(client).isNotNull()
        assertThat(client.internalId).isNull()
        val myInternalId = "my-internal-id"
        client.internalId =  myInternalId
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, client, DEFAULT_COMPANY_ID)

        val myCardUUID = 90L

        addCardToCompanyPool(myCardUUID, DEFAULT_COMPANY_ID)
        val clientData = ClientData()
        clientData.internalId = myInternalId
        clientData.cardUUID = myCardUUID
        clientData.name = "dd"
        clientData.lastName = "ll"
        clientData.phone = "375290000000"
        clientData.birthday = DateTime.now().minusYears(30)
        var ex: Exception? = null
        try {
            val createdClientData = companyFacade.createClient(clientData, DEFAULT_COMPANY_ID)
        } catch (e: Exception){
            ex = e
        }
        assertThat(ex).isNotNull()
        assertThat(ex).isOfAnyClassIn(NotAllowedException::class.java)
        if( ex is NotAllowedException){
            assertThat(ex.apiErrorCode).isEqualTo(ApiErrorCode.INTERNAL_ID_IS_ALREADY_BUSY)
        }
    }


    @Test
    fun test_NotAllowingUpdateClientWithTheSameInternalId(){
        val clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(clientCard).isNotNull()
        val client = clientCard.client
        assertThat(client).isNotNull()
        assertThat(client.internalId).isNull()
        client.internalId = DEFAULT_CLIENT_INTERNAL_ID
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, client, DEFAULT_COMPANY_ID)


        val anotherCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, ANOTHER_CARD_UUID)
        assertThat(anotherCard).isNotNull()
        val anotherClient = anotherCard.client
        anotherClient.internalId = DEFAULT_CLIENT_INTERNAL_ID
        var ex: Exception? = null
        try {
            companyFacade.updateClientByCardUUID(ANOTHER_CARD_UUID, anotherClient, DEFAULT_COMPANY_ID)
        } catch (e: Exception){
            ex = e
        }
        assertThat(ex).isNotNull()
        assertThat(ex).isOfAnyClassIn(NotAllowedException::class.java)
        if( ex is NotAllowedException){
            assertThat(ex.apiErrorCode).isEqualTo(ApiErrorCode.INTERNAL_ID_IS_ALREADY_BUSY)
        }
    }

    fun addCardToCompanyPool(cardUUID: Long, companyId: String) {
        val cardData = CardData();
        cardData.deliveredToCompany = companyId
        cardData.isFree = true
        cardData.cardUUID = cardUUID

        cardFacade.save(cardData)
    }

}