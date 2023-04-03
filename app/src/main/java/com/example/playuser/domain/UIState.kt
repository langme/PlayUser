package com.example.playuser.domain

data class UIState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",

    val hasFirstNameError: Boolean = false,
    val hasLastNameError: Boolean = false,
    val hasEmailError: Boolean = false,

    )

enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
    EDIT,
    REMOVE
}
