package com.example.playuser.domain

sealed class UIEvent {
    enum class TypeField {
        FIRST, LAST , EMAIL
    }
    data class FirstNameChanged(val firstName: String): UIEvent()
    data class LastNameChanged(val lastName: String): UIEvent()
    data class EmailChanged(val email: String): UIEvent()
    object Submit: UIEvent()
    object AllUsers: UIEvent()
}