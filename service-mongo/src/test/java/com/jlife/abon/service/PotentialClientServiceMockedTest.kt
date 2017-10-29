package com.jlife.abon.facade

import com.jlife.abon.entity.Client
import com.jlife.abon.repository.ClientRepository
import com.jlife.abon.service.ClientService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
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
class PotentialClientServiceMockedTest {

    @InjectMocks @Spy lateinit var clientService: ClientService
    @Mock lateinit var clientRepository: ClientRepository

    val client = Client()
    val returnedClient = Client()

    val companyId = "c1"

    private val comment = "comment"

    private val oldCardUUID = 777L

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        client.companyId = companyId
        client.comment = comment
        client.isPotential = true
        client.cardUUID = oldCardUUID
        doReturn(returnedClient).whenever(clientRepository).save(any<Client>())
    }

    @Test fun test_userBecameReal_whenMakingReal() {
        doReturn(client).whenever(clientService).getClientWithCardUUID(oldCardUUID, companyId)
        val clientResult = clientService.makeClientReal(oldCardUUID, companyId)

        assertEquals(returnedClient, clientResult)
        argumentCaptor<Client>().apply {
            verify(clientRepository, times(1)).save(capture())
            assertEquals(false, firstValue.isPotential)
        }
    }

}