package com.jlife.abon.facade.impl

import com.jlife.abon.dto.ClientData
import com.jlife.abon.entity.User
import com.jlife.abon.facade.PotentialClientFacade
import com.jlife.abon.util.generateRandomPassword
import org.springframework.stereotype.Service

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
@Service
class PotentialClientFacadeImpl : AbstractFacade(), PotentialClientFacade {

    override fun makeClientRealWithReplacing(cardUUID: Long, companyId: String, newCardUUID: Long): ClientData {
        cardService.verifyCardForRegister(newCardUUID, companyId)
        val client = clientService.makeClientReal(cardUUID, companyId)
        val user: User = userService.makeUserReal(cardUUID)
        val password = generateRandomPassword()
        userService.setNewPasswordByExternal(user.id, password)
        cardService.replaceWithNewCardAsCompany(user.id, newCardUUID, companyId)
        smsService.sendInitialPasswordBySms(user.phone, user.id, newCardUUID, password)
        val updatedClient = clientService.getClientWithCardUUID(newCardUUID, companyId)
        return dataMapper.toClientData(updatedClient)
    }

    override fun makeClientReal(cardUUID: Long, companyId: String): ClientData {
        val client = clientService.makeClientReal(cardUUID, companyId)
        val user: User = userService.makeUserReal(cardUUID)
        val password = generateRandomPassword()
        userService.setNewPasswordByExternal(user.id, password)
        smsService.sendInitialPasswordBySms(user.phone, user.id, cardUUID, password)
        return dataMapper.toClientData(client)
    }

    override fun selfRegisterClient(clientData: ClientData, companyId: String): ClientData {
        val company = companyService.getCompany(companyId)
        val widgetSetting = widgetSettingService.getWidgetSettingByCompanyId(companyId)
        val sendNotification = widgetSetting != null && widgetSetting.isNewClientEmailNotification
        val toEmail = widgetSetting?.notificationEmail
        clientData.companyId = companyId

        val client = dataMapper.fromClientData(clientData)
        val createdUser = userService.selfRegisterFromClient(client)
        val createdClient = clientService.createClient(createdUser, companyId, client.comment)
        val storedClientData = dataMapper.toClientData(createdClient)

        if (sendNotification && toEmail != null) {
            emailService.sendSelfRegisterClient(createdClient, company, toEmail)
        } else {
            LOG.error("Can't send client self register email to company {}, cardUUID {}" +
                    " because notification email is not setup or not enabled",
                    company.name, client.cardUUID)
        }
        return storedClientData
    }
}