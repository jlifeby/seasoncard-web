package com.jlife.abon.facade.impl

import com.jlife.abon.facade.ChangingPhoneFacade
import com.jlife.abon.util.generateRandomPassword
import org.springframework.stereotype.Service

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
@Service
class ChangingPhoneFacadeImpl : AbstractFacade(), ChangingPhoneFacade {

    override fun changeClientPhone(cardUUID: Long, newPhone: String, companyId: String) {
        val newPassword = generateRandomPassword();
        val user = userService.setNewPhone(cardUUID, newPhone, newPassword, companyId)
        clientService.updateClientPhonesByCardUUID(cardUUID, newPhone);
        smsService.sendInitialPasswordBySms(newPhone, user.id, cardUUID, newPassword)
    }
}
