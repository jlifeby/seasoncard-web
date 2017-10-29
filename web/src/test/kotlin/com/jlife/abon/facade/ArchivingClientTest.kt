package com.jlife.abon.facade

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 * @author Dzmitry Misiuk
 */
class ArchivingClientTest : ServiceBaseTestCase() {

    @Test
    fun test_clientNotActive_whenArchived() {
        val clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(clientCard.client.isActive).isTrue()

        companyFacade.archiveClient(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID)
        val updatedClient = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(updatedClient.client.isActive).isFalse()
    }

    @Test
    fun test_clientActive_whenRestored() {
        val clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(clientCard.client.isActive).isTrue()

        companyFacade.archiveClient(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID)
        val updatedClient = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(updatedClient.client.isActive).isFalse()

        companyFacade.restoreClient(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID)
        val restoredClient = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);

        assertThat(restoredClient.client.isActive).isTrue()
    }
}