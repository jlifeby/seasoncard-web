package com.jlife.abon.service

import com.jlife.abon.entity.Client
import com.jlife.abon.entity.Company
import com.jlife.abon.entity.CompanySettings
import com.jlife.abon.service.impl.EmailServiceImpl
import com.jlife.mailsender.MailSender
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
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
class EmailServiceSendSelfRegisterEmailTest {

    @InjectMocks @Spy val emailService = EmailServiceImpl()

    @Mock lateinit var mailSender: MailSender
    @Mock lateinit var templateService: TemplateService

    @Spy val company = Company()
    @Spy val companySettings = CompanySettings()
    @Spy val client = Client()

    val email = "c@example.com"
    val replyEmail = "noReply@example.com"
    val content = "rendered content"

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        company.companySettings = companySettings
        var map = mapOf(
                "client" to client,
                "company" to company
        )
        doReturn(content).whenever(templateService).createFromTemplate(EmailServiceImpl.CLIENT_SELF_REGISTER_NOTIFICATION_TEMPLATE, map)
        doReturn(replyEmail).whenever(emailService).noReplyEmail
    }

    @Test fun test_sendingEmailWithTemplateAndMap_whenExistNotificationEmail() {
        companySettings.notificationEmail = email
        emailService.sendSelfRegisterClient(client, company, email)
        verify(mailSender, times(1)).sendContentWithFromAsync(
                replyEmail,
                email,
                EmailServiceImpl.CLIENT_SELF_REGISTER_NOTIFICATION_SUBJECT,
                content,
                true)
    }
}