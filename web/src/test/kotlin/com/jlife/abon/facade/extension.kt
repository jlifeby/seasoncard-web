package com.jlife.abon.facade

import com.jlife.abon.TransactionType
import com.jlife.abon.dto.billing.AccountData
import com.jlife.abon.dto.billing.ManualReplenishmentDetailsData
import com.jlife.abon.dto.billing.TransactionData
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 * @author Dzmitry Misiuk
 */

fun BillingFacade.doManualReplenishment(account: AccountData, value: BigDecimal) {
    val transactionData = TransactionData()
    transactionData.apply {
        currency = account.currency
        this.value = value
        accountId = account.id
        type = TransactionType.REPLENISHMENT
    }

    val details = ManualReplenishmentDetailsData()
    details.comment = "based on ..."

    transactionData.details = details

    this.doReplenishment(transactionData)
}

val MAX_PAGE_REQUEST = PageRequest(0, Int.MAX_VALUE)
