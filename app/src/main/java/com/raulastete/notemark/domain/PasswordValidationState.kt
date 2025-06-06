package com.raulastete.notemark.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasSymbol: Boolean = false,
) {
    val isValid: Boolean
        get() = hasMinLength && (hasNumber || hasSymbol)
}