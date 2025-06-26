package com.carlodips.simpleloginregistration.domain.validation

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
