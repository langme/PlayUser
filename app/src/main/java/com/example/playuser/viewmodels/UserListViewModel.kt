package com.example.playuser.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playuser.data.User
import com.example.playuser.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject internal constructor(
    userRepository: UserRepository,
) : ViewModel() {

    private var _flowData = MutableStateFlow<List<User>>(emptyList())
    private val uiState = MutableStateFlow(UserListViewState())

    fun consumableState() = uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUsers()
                .collect { user ->
                    _flowData.value = user
                    uiState.value = uiState.value.copy(isLoading = false, users = _flowData.value)
                }
        }
    }

    fun handleViewEvent(viewEvent: UserListViewEvent){
        when(viewEvent){
            is UserListViewEvent.EditItem -> {
                //appNavigator.tryNavigateTo(Destination.UserDetailsScreen(uiState.value))
                val currentState = uiState.value

                // to call UI EDIT PROFILE
                /*val items = currentState.users.toMutableList().find { it.userId == viewEvent.user.userId }.apply{
                    viewEvent.user
                }.toList()
                uiState.value = uiState.value.copy(users = items)
                */


            }

            is UserListViewEvent.RemoveItem -> {
                val currentState = uiState.value
                val items = currentState.users.toMutableList().apply{
                    remove(viewEvent.user)
                }.toList()

                uiState.value = uiState.value.copy(users = items)
            }
        }
    }
}

data class UserListViewState(val isLoading: Boolean = true, val users: List<User> = emptyList())
sealed class UserListViewEvent{
    data class EditItem(val user: User): UserListViewEvent()
    data class RemoveItem(val user: User): UserListViewEvent()
}
