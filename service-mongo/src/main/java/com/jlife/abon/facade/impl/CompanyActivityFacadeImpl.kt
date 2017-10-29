package com.jlife.abon.facade.impl

import com.jlife.abon.dto.ClientActivityData
import com.jlife.abon.facade.CompanyActivityFacade
import com.jlife.abon.repository.AbonnementRepository
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
@Service
class CompanyActivityFacadeImpl : CompanyActivityFacade, AbstractFacade() {
    @Autowired
    lateinit var abonnementRepository: AbonnementRepository

    override fun getClientActivities(companyId: String?, startDate: DateTime?, endDate: DateTime?): MutableList<ClientActivityData> {
        val abonnements = abonnementRepository.findAbonsWithAtLeastOneAttendanceOrPurchasedByCompanyBetween(companyId, startDate, endDate)
        val abonnementsGrouppedByCardId = abonnements.groupBy { it.cardId }
        val activities = abonnementsGrouppedByCardId.map {
            val (cardId, abons) = it
            val card = cardService.getCard(cardId)
            val clientActivity = ClientActivityData()
            val client = clientService.getClientWithCardUUID(card.cardUUID, companyId)
            val purchaseDates = abons.filter { it.purchaseDate.isBefore(endDate) && it.purchaseDate.isAfter(startDate) }.map { it.purchaseDate }
            val attendanceDates = abons.flatMap { it.attendances }.filter { it.visitDate.isBefore(endDate) && it.visitDate.isAfter(startDate) }.map { it.visitDate }
            clientActivity.cardUUID = card.cardUUID
            clientActivity.client = dataMapper.toClientData(client)
            clientActivity.purchaseDates = purchaseDates
            clientActivity.attendanceDates = attendanceDates
            clientActivity
        }.toMutableList()
        return activities
    }

}