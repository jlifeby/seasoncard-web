package com.jlife.abon.service.sms

import com.jlife.abon.entity.SmsMessage
import com.jlife.abon.enumeration.SmsStatus
import com.jlife.abon.sms.SmsErrorResponse
import com.jlife.abon.sms.SmsSuccessResponse
import infobip.api.client.SendSingleTextualSms
import infobip.api.config.BasicAuthConfiguration
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest
import org.joda.time.DateTime
import java.util.*

class InfoBipSmsService : AbstractSmsService() {

    companion object {
        const val STATUS_GROUP_ID = "status_groupId"
        const val STATUS_ID = "status_id"
        const val STATUS_DESCRIPTION = "status_description"
        const val STATUS_ACTION = "status_action"
    }

    override fun sendSmsAsync(phone: String?, text: String?, isPriority: Boolean, storedSmsMessage: SmsMessage?) {
        val phones = phone?.let { p -> mutableListOf(p) }
        val storedSmsMessages = storedSmsMessage?.let { sms -> mutableMapOf(sms.phone to sms)}
        sendSmsAsync(phones, text, isPriority, storedSmsMessages)
    }

    override fun sendSmsAsync(phonesTo: MutableList<String>?, text: String?, isPriority: Boolean, storedSmsMessages: MutableMap<String, SmsMessage>?) {
        val client = SendSingleTextualSms(BasicAuthConfiguration(username, password))
        val requestBody = SMSTextualRequest()
        requestBody.from = senderName
        requestBody.to = phonesTo
        requestBody.text = text
        val smsResponse = client.execute(requestBody)
        smsResponse.messages.map { resp -> {
            val storedSmsMessage = storedSmsMessages?.get(resp.to)
            storedSmsMessage?.sentDate = DateTime()
            val messageUuid = UUID.fromString(resp.messageId)
            val smsCount = resp.smsCount
            // see https://dev.infobip.com/v1/docs/response-codes
            if (resp.status.groupId == 0) {
                val smsSuccessResponse = SmsSuccessResponse()
                smsSuccessResponse.uuid = messageUuid
                smsSuccessResponse.status = SmsStatus.SENT
                smsSuccessResponse.smsCount = smsCount
                storedSmsMessage?.successResponse = smsSuccessResponse
            }
            else {
                val smsErrorResponse = SmsErrorResponse(resp.status.description)
                smsErrorResponse.uuid = messageUuid
                smsErrorResponse.customFields[STATUS_GROUP_ID] = resp.status.groupId.toString()
                smsErrorResponse.customFields[STATUS_ID] = resp.status.id.toString()
                smsErrorResponse.customFields[STATUS_DESCRIPTION] = resp.status.description
                smsErrorResponse.customFields[STATUS_ACTION] = resp.status.action
                storedSmsMessage?.errorResponse = smsErrorResponse
            }
            smsMessageRepository.save<SmsMessage>(storedSmsMessage)
        } }
    }
}

