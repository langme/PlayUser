package com.example.playuser.domain

sealed class ValidationEvent{
    object Success: ValidationEvent()
    object Error: ValidationEvent()
}
