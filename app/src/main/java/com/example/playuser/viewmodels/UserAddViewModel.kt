package com.example.playuser.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.example.playuser.data.User
import com.example.playuser.data.UserRepository
import com.example.playuser.domain.UIEvent
import com.example.playuser.domain.UIState
import com.example.playuser.domain.ValidationEvent
import com.example.playuser.domain.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAddViewModel @Inject internal constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private var _uiState = mutableStateOf(UIState())
    val uiState: State<UIState> = _uiState
    val validationEvent = MutableSharedFlow<ValidationEvent>()

    init {
    }

    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.FirstNameChanged -> {
                _uiState.value = _uiState.value.copy(
                    firstName = event.firstName
                )
            }
            is UIEvent.LastNameChanged -> {
                _uiState.value = _uiState.value.copy(
                    lastName = event.lastName
                )
            }
            is UIEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = event.email
                )
            }

            is UIEvent.Submit -> {
                validateInputs()
            }

            is UIEvent.AllUsers -> {
                //TODO
            }
        }
    }

    fun addUser(user : User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUserRepo(user)
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUsers()
        }
    }

    private fun validateInputs() {
        val firstNameResult = Validator.validateFirstName(_uiState.value.firstName)
        val lastNameResult = Validator.validateLastName(_uiState.value.lastName)
        val emailResult = Validator.validateEmail(_uiState.value.email)

        _uiState.value = _uiState.value.copy(
            hasFirstNameError = !firstNameResult.status,
            hasLastNameError = !lastNameResult.status,
            hasEmailError = !emailResult.status,
        )

        val hasError = listOf(
            firstNameResult,
            lastNameResult,
            emailResult
        ).any { !it.status }
        viewModelScope.launch {
            if (!hasError) {
                validationEvent.emit(ValidationEvent.Success)
            } else {
                validationEvent.emit(ValidationEvent.Error)
            }
        }
    }
}

