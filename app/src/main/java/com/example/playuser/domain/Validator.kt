package com.example.playuser.domain

object Validator {
    fun validateFirstName(firstName: String): ValidationResult {
        return ValidationResult(firstName.isNotEmpty() && firstName.length > 2)
    }
    fun validateLastName(lastName: String): ValidationResult {
        return ValidationResult(lastName.isNotEmpty() && lastName.length > 2)
    }
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }
}

data class ValidationResult(
    val status: Boolean = false,
)