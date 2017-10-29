package com.jlife.abon.facade

import com.jlife.abon.audit.JunitAuditor
import com.jlife.abon.entity.SmsMessage
import com.jlife.abon.enumeration.SmsType
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
class ChangingPhoneByCompanyAdminTest : ServiceBaseTestCase() {

    val newPhone = "375440000000"

    @Autowired
    lateinit var changingPhoneFacade: ChangingPhoneFacade

    @Autowired
    lateinit var smsFacade: SmsFacade

    @Test
    fun test_phoneIsUpdatedInUserAndClient_whenChange() {
        var clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        var user = userFacade.findOneByCardUUID(DEFAULT_CARD_UUID)

        assertThat(user.phone).isNotEqualTo(newPhone)
        assertThat(clientCard.client.phone).isNotEqualTo(newPhone)
        changingPhoneFacade.changeClientPhone(DEFAULT_CARD_UUID, newPhone, DEFAULT_COMPANY_ID)

        clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        user = userFacade.findOneByCardUUID(DEFAULT_CARD_UUID)

        assertThat(user.phone).isEqualTo(newPhone)
        assertThat(clientCard.client.phone).isEqualTo(newPhone)
    }

    @Test
    fun test_newPasswordSetup_whenUpdatePhone() {
        var user = userFacade.findOneByCardUUID(DEFAULT_CARD_UUID)
        val oldHashedPassword = user.password

        changingPhoneFacade.changeClientPhone(DEFAULT_CARD_UUID, newPhone, DEFAULT_COMPANY_ID)

        user = userFacade.findOneByCardUUID(DEFAULT_CARD_UUID)
        assertThat(oldHashedPassword).isNotEqualTo(user.password)
    }

    @Test
    fun test_newPasswordSentBySms_whenUpdatePhone() {
        cleanCollections(SmsMessage::class.java)
        var allMessages = smsFacade.allMessages
        assertThat(allMessages).isEmpty()

        changingPhoneFacade.changeClientPhone(DEFAULT_CARD_UUID, newPhone, DEFAULT_COMPANY_ID)
        allMessages = smsFacade.allMessages

        assertThat(allMessages).isNotEmpty().hasSize(1)
        assertThat(allMessages[0].smsType).isEqualTo(SmsType.INITIAL_PASSWORD)
    }

    @Test
    fun test_changingPhoneHistoryIsStoredInUser_whenUpdatedPhone() {
        var startDate = DateTime()
        var user = userFacade.findOneByCardUUID(DEFAULT_CARD_UUID)
        val oldPhone = user.phone
        assertThat(user.phoneChangings).isNotNull().isEmpty()


        changingPhoneFacade.changeClientPhone(DEFAULT_CARD_UUID, newPhone, DEFAULT_COMPANY_ID)


        user = userFacade.findOneByCardUUID(DEFAULT_CARD_UUID)
        assertThat(user.phoneChangings).isNotNull().hasSize(1)
        val phoneChangingData = user.phoneChangings[0]

        assertThat(phoneChangingData).isNotNull()

        assertThat(phoneChangingData.newPhone).isEqualTo(newPhone)
        assertThat(phoneChangingData.oldPhone).isEqualTo(oldPhone)
        assertThat(phoneChangingData.createdDate).isBetween(startDate, DateTime())

        assertThat(phoneChangingData.createdBy).isEqualTo(JunitAuditor.JUNIT_AUDITOR)
    }

}
