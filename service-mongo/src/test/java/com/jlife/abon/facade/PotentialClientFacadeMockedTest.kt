package com.jlife.abon.facade

import com.jlife.abon.dto.ClientData
import com.jlife.abon.entity.Client
import com.jlife.abon.entity.Company
import com.jlife.abon.entity.User
import com.jlife.abon.entity.WidgetSetting
import com.jlife.abon.error.AbonRuntimeException
import com.jlife.abon.error.ApiErrorCode
import com.jlife.abon.facade.impl.PotentialClientFacadeImpl
import com.jlife.abon.mapper.DataMapperImpl
import com.jlife.abon.service.CardService
import com.jlife.abon.service.ClientService
import com.jlife.abon.service.CompanyService
import com.jlife.abon.service.EmailService
import com.jlife.abon.service.UserService
import com.jlife.abon.service.WidgetSettingService
import com.jlife.abon.service.sms.SmsService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
class PotentialClientFacadeMockedTest {

    @InjectMocks val potentialClientFacade: PotentialClientFacade = PotentialClientFacadeImpl()

    @Mock lateinit var userService: UserService

    @Mock lateinit var clientService: ClientService

    @Mock lateinit var widgetService: WidgetSettingService

    @Mock lateinit var emailService: EmailService

    @Mock lateinit var companyService: CompanyService

    @Mock lateinit var cardService: CardService

    @Mock lateinit var smsService: SmsService

    @Spy val widgetSetting = WidgetSetting()

    @Spy val dataMapper = DataMapperImpl()

    val clientData = ClientData()

    val client = Client()

    val user = User()

    val company = Company()

    val companyId = "c1"

    private val toEmail = "1@example.com"

    private val comment = "comment"

    private val oldCardUUID = 777L
    private val newCardUUID = 888L
    private val userId = "userId"
    private val userPhone = "375290000000"

    @Before fun setUp() {
        client.companyId = companyId
        client.comment = comment
        MockitoAnnotations.initMocks(this)
        doReturn(client).whenever(dataMapper).fromClientData(clientData)
        doReturn(widgetSetting).whenever(widgetService).getWidgetSettingByCompanyId(companyId)
        doReturn(toEmail).whenever(widgetSetting).notificationEmail
        doReturn(client).whenever(clientService).createClient(user, companyId, comment)
        doReturn(user).whenever(userService).selfRegisterFromClient(client)
        doReturn(company).whenever(companyService).getCompany(companyId)
    }

    @Test fun test_emailServiceInvoked_whenCreatePotentialClientAndSetNotifying() {
        doReturn(true).whenever(widgetSetting).isNewClientEmailNotification
        potentialClientFacade.selfRegisterClient(clientData, companyId)
        verify(widgetService, times(1)).getWidgetSettingByCompanyId(companyId)
        verify(emailService, times(1)).sendSelfRegisterClient(client, company, toEmail)
    }

    @Test fun test_emailServiceNotInvoked_whenCreatePotentialClientAndNotSetNotifying() {
        doReturn(false).whenever(widgetSetting).isNewClientEmailNotification
        potentialClientFacade.selfRegisterClient(clientData, companyId)
        verify(widgetService, times(1)).getWidgetSettingByCompanyId(companyId)
        verify(emailService, times(0)).sendSelfRegisterClient(any(), any(), any())
    }

    @Test fun test_ReplacingIsInvoked_whenMakingReal() {
        user.isPotential = false
        user.id = userId
        user.phone = userPhone
        doReturn(user).whenever(userService).makeUserReal(oldCardUUID)
        client.isPotential = false
        client.cardUUID = newCardUUID
        doReturn(client).whenever(clientService).makeClientReal(oldCardUUID, companyId)
        doReturn(clientData).whenever(dataMapper).toClientData(client)
        doReturn(client).whenever(clientService).getClientWithCardUUID(newCardUUID, companyId)

        potentialClientFacade.makeClientRealWithReplacing(oldCardUUID, companyId, newCardUUID)
        verify(cardService, times(1)).verifyCardForRegister(newCardUUID, companyId)
        verify(userService, times(1)).makeUserReal(oldCardUUID)
        verify(clientService, times(1)).makeClientReal(oldCardUUID, companyId)
        verify(cardService, times(1)).replaceWithNewCardAsCompany(userId, newCardUUID, companyId)
        verify(smsService, times(1)).sendInitialPasswordBySms(eq(userPhone), eq(userId), eq(newCardUUID), any())
        verify(clientService, times(1)).getClientWithCardUUID(newCardUUID, companyId)
    }

    @Test fun test_ReplacingIsNotInvoked_whenMakingRealWithNotAllowedCard() {
        user.isPotential = false
        user.id = userId
        user.phone = userPhone
        doReturn(user).whenever(userService).makeUserReal(oldCardUUID)
        client.isPotential = false
        client.cardUUID = newCardUUID
        doReturn(client).whenever(clientService).makeClientReal(oldCardUUID, companyId)
        doReturn(clientData).whenever(dataMapper).toClientData(client)
        doThrow(AbonRuntimeException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_UUID, "$newCardUUID"))
                .whenever(cardService).verifyCardForRegister(newCardUUID, companyId)
        var are: AbonRuntimeException? = null
        try {
            potentialClientFacade.makeClientRealWithReplacing(oldCardUUID, companyId, newCardUUID)
        } catch (e: AbonRuntimeException) {
            are = e
        }
        verify(cardService, times(1)).verifyCardForRegister(newCardUUID, companyId)
        verify(userService, times(0)).makeUserReal(oldCardUUID)
        verify(clientService, times(0)).makeClientReal(oldCardUUID, companyId)
        verify(cardService, times(0)).replaceWithNewCardAsCompany(userId, newCardUUID, companyId)
        verify(smsService, times(0)).sendInitialPasswordBySms(eq(userPhone), eq(userId), eq(newCardUUID), any())
        assertThat(are).isNotNull()
    }


}