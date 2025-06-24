package com.raulastete.notemark.domain.validator

data class UsernameValidationState(
    val greaterThanMinLength: Boolean = false,
    val lowerThanMaxLength: Boolean = false,
)