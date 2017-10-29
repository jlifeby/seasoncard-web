package com.jlife.abon.facade

import com.jlife.abon.dto.CardData
import com.jlife.abon.dto.ClientData
import com.jlife.abon.entity.Card
import com.jlife.abon.entity.Client
import com.jlife.abon.entity.SmsMessage
import com.jlife.abon.entity.User
import com.jlife.abon.enumeration.Role
import com.jlife.abon.enumeration.SmsType
import com.jlife.abon.error.AbonRuntimeException
import com.jlife.abon.error.ApiErrorCode
import com.jlife.abon.interfaces.Existence.ACTIVE_STATUS
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
class PotentialClientFacadeTest : ServiceBaseTestCase() {


    @Autowired lateinit var potentialClientFacade: PotentialClientFacade
    @Autowired lateinit var clientFacade: ClientFacade
    @Autowired lateinit var smsFacade: SmsFacade

    val name = "junit_self_name"
    val email = "self@junit.com"
    val phone = "375250000000"
    val FREE_VIRTUAL_CARD_UUID = 777L

    @Test
    fun test_newUserCreated_whenClientSelfRegistered() {
        val initialSizeOfUsers = userFacade.allUsers.size

        val clientData = selfRegisterWithPreparing()

        val countOfUsers = userFacade.allUsers.size
        assertThat(initialSizeOfUsers + 1).isEqualTo(countOfUsers)
        assertThat(clientData.userId).isNotNull()

        val userData = userFacade.findById(clientData.userId)
        assertThat(userData).isNotNull()
        assertThat(userData.name).isEqualTo(name)
        assertThat(userData.newEmail).isEqualTo(email)
        assertThat(userData.phone).isEqualTo(phone)
        assertThat(userData.cardUUID).isEqualTo(FREE_VIRTUAL_CARD_UUID)
        assertThat(userData.status).isEqualTo(ACTIVE_STATUS)
        assertThat(userData.roles).isNotEmpty().hasSize(1)
        assertThat(userData.roles).containsOnly(Role.ROLE_USER.name)
    }

    @Test
    fun test_newUserIsRelatedToCard_whenSelfCreated() {
        cleanCollections(User::class.java)
        val clientData = selfRegisterWithPreparing()
        val userData = userFacade.findById(clientData.userId)
        assertThat(userData.cardUUID).isEqualTo(FREE_VIRTUAL_CARD_UUID)
    }

    @Test
    fun test_newClientCreated_whenClientSelfRegistered() {
        cleanCollections(Client::class.java)
        val initialSizeOfUsers = clientFacade.findAllClients(MAX_PAGE_REQUEST).totalElements

        val clientData = selfRegisterWithPreparing()

        val countOfClients = clientFacade.findAllClients(MAX_PAGE_REQUEST).totalElements
        assertThat(initialSizeOfUsers + 1).isEqualTo(countOfClients)
        assertThat(clientData.id).isNotNull()

        val storedClientData = clientFacade.findAllClients(MAX_PAGE_REQUEST).content[0]
        assertThat(storedClientData).isNotNull()
        assertThat(storedClientData.name).isEqualTo(name)
        assertThat(storedClientData.phone).isEqualTo(phone)
        assertThat(storedClientData.userId).isEqualTo(clientData.userId)
    }

    @Test
    fun test_newClientIsRelatedToCard_whenSelfCreated() {
        cleanCollections(Client::class.java)
        val clientData = selfRegisterWithPreparing()
        val storedClientData = clientFacade.findAllClients(MAX_PAGE_REQUEST).content[0]
        assertThat(storedClientData.cardUUID).isEqualTo(FREE_VIRTUAL_CARD_UUID)
    }

    @Test
    fun test_newClientCanNotBeCreated_whenThereAreNotFreeCard() {
        cleanCollections(Card::class.java)
        val rowClientData = ClientData()
        rowClientData.name = name
        rowClientData.phone = phone
        rowClientData.email = email

        var are: AbonRuntimeException? = null
        try {
            potentialClientFacade.selfRegisterClient(rowClientData, DEFAULT_COMPANY_ID)
        } catch (e: AbonRuntimeException) {
            are = e
        }
        assertThat(are).isNotNull()
        assertThat(are!!.apiErrorCode).isEqualTo(ApiErrorCode.NO_FREE_VIRTUAL_CARDS_TO_SELF_REGISTER_CLIENT)

    }

    @Test(expected = IllegalArgumentException::class)
    fun test_newClientFailed_whenEmptyName() {
        val rowClientData = ClientData()
        rowClientData.phone = phone
        rowClientData.email = email
        potentialClientFacade.selfRegisterClient(rowClientData, DEFAULT_COMPANY_ID)
    }

    @Test
    fun test_newClientFailed_whenEmptyEmail() {
        prepareFreeVirtualCard()
        val rowClientData = ClientData()
        rowClientData.name = name
        rowClientData.phone = phone
        potentialClientFacade.selfRegisterClient(rowClientData, DEFAULT_COMPANY_ID)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_newClientFailed_whenEmptyPhone() {
        val rowClientData = ClientData()
        rowClientData.name = name
        rowClientData.email = email
        potentialClientFacade.selfRegisterClient(rowClientData, DEFAULT_COMPANY_ID)
    }


    @Test
    fun test_newClientAndUserIsPotential_whenSelfRegistered() {
        val clientData = selfRegisterWithPreparing()
        val userData = userFacade.findById(clientData.userId)

        assertThat(clientData.isPotential).isTrue()
        assertThat(userData.isPotential).isTrue()
    }

    @Test
    fun test_newClientHasSubscriptionsForEmailSms_whenSelfRegistered() {
        val clientData = selfRegisterWithPreparing()
        val userData = userFacade.findById(clientData.userId)
        assertThat(userData.isAllowedEmailReceiving).isTrue()
        assertThat(userData.isAllowedSmsReceiving).isTrue()
    }

    @Test
    fun test_SMSNotSend_whenClientSelfRegistered() {
        cleanCollections(SmsMessage::class.java)
        selfRegisterWithPreparing()

        val allMessages = smsFacade.allMessages
        assertThat(allMessages).isEmpty()
    }

    @Test
    fun test_cardIsOccupiedAndInitializingCompanyIsNull_whenClientSelfRegistered() {
        prepareFreeVirtualCard()
        var card = cardFacade.findOneByCardUUID(FREE_VIRTUAL_CARD_UUID)
        assertThat(card.isFree).isTrue()
        assertThat(card.userId).isNull()

        val registeredCard = registerClient()
        card = cardFacade.findOneByCardUUID(FREE_VIRTUAL_CARD_UUID)
        assertThat(card.isFree).isFalse()
        assertThat(card.initializingCompany).isEqualTo(DEFAULT_COMPANY_ID)
        assertThat(card.userId).isEqualTo(registeredCard.userId)
    }

    @Test
    fun test_cardIsDeliveredToCompanyFromPull_whenClientSelfRegistered() {
        prepareFreeVirtualCard()
        var card = cardFacade.findOneByCardUUID(FREE_VIRTUAL_CARD_UUID)
        assertThat(card.deliveredToCompany).isNull()

        val selfRegisterWithPreparing = registerClient()
        card = cardFacade.findOneByCardUUID(FREE_VIRTUAL_CARD_UUID)
        assertThat(card.deliveredToCompany).isEqualTo(DEFAULT_COMPANY_ID)
    }

    @Test
    fun test_whenEmailIsBusy_whenSelfRegistered() {
        val rowClientData = ClientData()
        rowClientData.name = name
        rowClientData.phone = phone
        rowClientData.email = DEFAULT_USER_EMAIL
        var are: AbonRuntimeException? = null
        try {
            potentialClientFacade.selfRegisterClient(rowClientData, DEFAULT_COMPANY_ID)
        } catch (e: AbonRuntimeException) {
            are = e
        }
        assertThat(are).isNotNull()
        assertThat(are!!.apiErrorCode).isEqualTo(ApiErrorCode.USER_ALREADY_EXISTS_WITH_EMAIL)
    }

    @Test
    fun test_clientBecomeReal_whenMakeIsReal() {
        cleanCollections(Client::class.java)
        selfRegisterWithPreparing()
        potentialClientFacade.makeClientReal(FREE_VIRTUAL_CARD_UUID, DEFAULT_COMPANY_ID)
        val storedClientData = clientFacade.findAllClients(MAX_PAGE_REQUEST).content[0]
        assertThat(storedClientData.isPotential).isFalse()
    }

    @Test
    fun test_userBecomeReal_whenMakeIsReal() {
        cleanCollections(Client::class.java)
        val clientData = selfRegisterWithPreparing()
        potentialClientFacade.makeClientReal(FREE_VIRTUAL_CARD_UUID, DEFAULT_COMPANY_ID)
        val userData = userFacade.findById(clientData.userId)
        assertThat(userData.isPotential).isFalse()
    }

    @Test
    fun test_sentPasswordToUser_whenMakeIsReal() {
        cleanCollections(Client::class.java)
        cleanCollections(SmsMessage::class.java)
        val clientData = selfRegisterWithPreparing()
        potentialClientFacade.makeClientReal(FREE_VIRTUAL_CARD_UUID, DEFAULT_COMPANY_ID)

        val allMessages = smsFacade.allMessages
        assertThat(allMessages).hasSize(1)
        val smsMessageData = allMessages[0]
        assertThat(smsMessageData.userId).isEqualTo(clientData.userId)
        assertThat(smsMessageData.phone).isEqualTo(clientData.phone)
        assertThat(smsMessageData.smsType).isEqualTo(SmsType.INITIAL_PASSWORD)
    }

    private fun selfRegisterWithPreparing(): ClientData {
        prepareFreeVirtualCard()
        return registerClient()
    }

    private fun prepareFreeVirtualCard() {
        cleanCollections(Card::class.java)
        cleanCollections(SmsMessage::class.java)
        val card = CardData()
        card.cardUUID = FREE_VIRTUAL_CARD_UUID
        card.isFree = true
        cardFacade.save(card)
    }

    private fun registerClient(): ClientData {
        val rowClientData = ClientData()
        rowClientData.name = name
        rowClientData.phone = phone
        rowClientData.email = email
        val clientData = potentialClientFacade.selfRegisterClient(rowClientData, DEFAULT_COMPANY_ID)
        return clientData
    }
}
