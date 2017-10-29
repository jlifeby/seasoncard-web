package com.jlife.abon.criteria

import com.jlife.abon.enumeration.ActiveClientSearchMode

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */
class ActiveClientCriteria(
        var searchMode: ActiveClientSearchMode = ActiveClientSearchMode.BOTH
) : SearchCriteria
