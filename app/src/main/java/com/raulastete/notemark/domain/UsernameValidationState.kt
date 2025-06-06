package com.raulastete.notemark.domain

data class UsernameValidationState(
    val greaterThanMinLength: Boolean = false,
    val lowerThanMaxLength: Boolean = false,
)