package com.carlodips.simpleloginregistration.domain.validation

object LoginValidationUtil {
    fun validateUserId(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }

    fun validatePassword(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}
