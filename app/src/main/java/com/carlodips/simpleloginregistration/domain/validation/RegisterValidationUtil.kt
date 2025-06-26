package com.carlodips.simpleloginregistration.domain.validation

object RegisterValidationUtil {
    fun validateUsernameInput(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Username cannot be blank"
            )
        }

        if (input.length < 4) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Invalid Length"
            )
        }

        val lettersNumbersPattern = Regex(RegexPatternUtil.LETTERS_NUMBERS_ONLY)

        if (!input.matches(lettersNumbersPattern)) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Invalid Character"
            )
        }

        return ValidationResult(isValid = true)
    }

    fun validateEmailInput(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Email cannot be blank"
            )
        }

        val emailPattern = Regex(RegexPatternUtil.EMAIL)

        if (!input.matches(emailPattern)) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Email is not valid"
            )
        }

        return ValidationResult(isValid = true)
    }

    fun validatePassword(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Password cannot be blank"
            )
        }

        if (input.length < 8) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Password should contain at least 8 characters"
            )
        }

        val hasDigitsAndLetters = input.any { it.isLetter() } && input.any { it.isDigit() }

        if (!hasDigitsAndLetters) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Password should contain at least one character and one digit"
            )
        }

        return ValidationResult(isValid = true)
    }

    fun doPasswordsMatch(password1: String, password2: String): ValidationResult {
        if (password2.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Password cannot be blank"
            )
        }

        if (password1 != password2) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Passwords does not match"
            )
        }

        return ValidationResult(isValid = true)
    }
}