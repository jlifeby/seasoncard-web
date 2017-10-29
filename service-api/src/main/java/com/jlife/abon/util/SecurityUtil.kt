package com.jlife.abon.util

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.

 * @author Dzmitry Misiuk
 */

fun generateRandomPassword(): String {
    val password = RandomStringUtils.randomAlphanumeric(6).toLowerCase()
    return password
}

fun encodeBCrypt(text: String): String {
    val hashedValue = BCryptPasswordEncoder().encode(text)
    return hashedValue
}
