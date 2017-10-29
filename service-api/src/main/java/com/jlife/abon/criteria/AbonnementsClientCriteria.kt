package com.jlife.abon.criteria

import com.jlife.abon.enumeration.AbonnementsClientSearchMode

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
class AbonnementsClientCriteria(
        val searchMode: AbonnementsClientSearchMode = AbonnementsClientSearchMode.ALL,
        val productId: String? = null,
        var expiringCriteria: ExpiringAbonementCriteria? = null
) : SearchCriteria