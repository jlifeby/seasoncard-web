package com.jlife.abon.facade

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 * @author Dzmitry Misiuk
 */
class TestPopulatingUserToClient : ServiceBaseTestCase() {

    @Test
    fun test_populatingUser_whenGetClients(){
        val clientPage = companyFacade.findClients(DEFAULT_COMPANY_ID, MAX_PAGE_REQUEST)

        val clients = clientPage.content
        assertThat(clients).isNotNull().isNotEmpty()
        val clientData = clients[0]
        assertThat(clientData.user).isNotNull()
    }
}