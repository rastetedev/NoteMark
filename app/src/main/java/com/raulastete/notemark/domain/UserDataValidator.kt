package com.raulastete.notemark.domain

class UserDataValidator(private val patternValidator: PatternValidator) {

    fun isValidUsername(username: String): UsernameValidationState {
        return UsernameValidationState(
            greaterThanMinLength = username.length >= MIN_USERNAME_LENGTH,
            lowerThanMaxLength = username.length <= MAX_USERNAME_LENGTH
        )
    }

    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun isValidPassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasNumber = password.any { it.isDigit() }
        val hasSymbol = password.any { it.isLetterOrDigit().not() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasNumber,
            hasSymbol = hasSymbol
        )
    }

    companion object {
        const val MIN_USERNAME_LENGTH = 3
        const val MAX_USERNAME_LENGTH = 20
        const val MIN_PASSWORD_LENGTH = 8
    }
}