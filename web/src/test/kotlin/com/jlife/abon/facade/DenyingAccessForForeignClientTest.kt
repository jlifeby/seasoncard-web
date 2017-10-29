package com.jlife.abon.facade

import com.jlife.abon.dto.AbonnementData
import com.jlife.abon.error.ApiErrorCode
import com.jlife.abon.error.NotAllowedException
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 * @author Dzmitry Misiuk
 */
class DenyingAccessForForeignClientTest : ServiceBaseTestCase() {

    @Autowired
    lateinit var enrollFacade: EnrollFacade

    @Test
    fun test_gettingOwnClient_whenExist() {
        val clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID)
        assertThat(clientCard).isNotNull()
        assertThat(clientCard.client).isNotNull()
    }

    @Test
    fun test_gettingForeignClient_whenNotExistAndGettingDirectly() {
        var exception: NotAllowedException? = null
        try {
            val clientCard = cardFacade.getClientCard(ANOTHER_COMPANY_ID, DEFAULT_CARD_UUID)
            assertThat(clientCard).isNotNull()
        } catch (e: NotAllowedException) {
            exception = e
        }
        assertThat(exception).isNotNull()
        assertThat(exception?.apiErrorCode).isEqualTo(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_CLIENT_WITH_CARD)
    }

    @Test
    fun test_gettingForeignClient_whenEnrollAbonement() {
        var exception: NotAllowedException? = null
        try {
            val abonnementData = AbonnementData()
            abonnementData.apply {
                productId = ANOTHER_PRODUCT_ID_BY_TIME
                startDate = DateTime()
                endDate = DateTime().plusMonths(1)
            }
            enrollFacade.enrollAbonnement(DEFAULT_CARD_UUID, ANOTHER_COMPANY_ID, abonnementData)
        } catch (e: NotAllowedException) {
            exception = e
        }
        assertThat(exception).isNotNull()
        assertThat(exception?.apiErrorCode).isEqualTo(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_CLIENT_WITH_CARD)
    }

}