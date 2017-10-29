package com.jlife.abon.service.impl

import by.jlife.analytics.dto.AnalyticItem
import by.jlife.analytics.dto.HourAttendance
import com.jlife.abon.entity.Abonnement
import com.jlife.abon.service.AbstractService
import com.jlife.abon.service.AnalyticService
import org.joda.time.DateTime
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*


/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
@Service
class AnalyticServiceImpl : AbstractService(), AnalyticService {
    override fun getForSoldProducts(startDate: DateTime, endDate: DateTime): MutableList<AnalyticItem> {
        val hourToCountMap = prepareMapForTimePeriod(endDate, startDate)

        val abonnementList = abonnementRepository.findByPurchaseDateBetween(startDate, endDate)

        for (abonnement in abonnementList) {
            val purchaseDated = abonnement.purchaseDate
            if (purchaseDated.isAfter(startDate) && purchaseDated.isBefore(endDate)) {
                val dateKey = purchaseDated.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0)
                val currentCount = hourToCountMap.getOrDefault(dateKey, 0)
                hourToCountMap.put(dateKey, currentCount + 1)
            }
        }

        return hourToCountMap.map { AnalyticItem(it.key, it.value) }.toMutableList()
    }

    override fun getForNewClients(startDate: DateTime, endDate: DateTime): MutableList<AnalyticItem> {
        val hourToCountMap = prepareMapForTimePeriod(endDate, startDate)

        val newClients = clientRepository.findByCreatedDateBetween(startDate, endDate)

        for (client in newClients) {
            val createdDate = client.createdDate
            if (createdDate.isAfter(startDate) && createdDate.isBefore(endDate)) {
                val dateKey = createdDate.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0)
                val currentCount = hourToCountMap.getOrDefault(dateKey, 0)
                hourToCountMap.put(dateKey, currentCount + 1)
            }
        }

        return hourToCountMap.map { AnalyticItem(it.key, it.value) }.toMutableList()
    }

    override fun getForAttendances(startDate: DateTime, endDate: DateTime): MutableList<HourAttendance> {
        val abonnementList = abonnementRepository.getAllAbonnementsWithAttendancesBetween(startDate, endDate)
        return prepareHourAttendanceList(abonnementList, startDate, endDate)
    }

    override fun getForAttendancesAsCompany(startDate: DateTime, endDate: DateTime, companyId: String): MutableList<HourAttendance> {
        Assert.notNull(companyId)

        val abonnementList = abonnementRepository.getAllAbonnementsForCompanyWithAttendancesBetween(companyId,
                startDate, endDate)
        return prepareHourAttendanceList(abonnementList, startDate, endDate)
    }


    /**
     * Private section
     */

    private fun prepareHourAttendanceList(abonnementList: MutableList<Abonnement>, startDate: DateTime, endDate: DateTime): MutableList<HourAttendance> {
        val hourToCountMap = prepareMapForTimePeriod(endDate, startDate)

        for (abonnement in abonnementList) {
            for (attendance in abonnement.attendances) {
                var visitDate = attendance.visitDate
                if (visitDate.isAfter(startDate) && visitDate.isBefore(endDate)) {
                    val dateKey = visitDate.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0)
                    val currentCount = hourToCountMap.getOrDefault(dateKey, 0)
                    hourToCountMap.put(dateKey, currentCount + 1)
                }
            }
        }

        return hourToCountMap.map { HourAttendance(it.key, it.value) }.toMutableList()
    }

    private fun prepareMapForTimePeriod(endDate: DateTime, startDate: DateTime): SortedMap<DateTime, Int> {
        val hourToCountMap = sortedMapOf<DateTime, Int>()

        var starDateKey = startDate.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0)
        while (starDateKey.isBefore(endDate)) {
            hourToCountMap.put(starDateKey, 0)
            starDateKey = starDateKey.plusHours(1)
        }
        return hourToCountMap
    }

}