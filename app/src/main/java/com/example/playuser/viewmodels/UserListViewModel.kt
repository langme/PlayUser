package com.example.playuser.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playuser.data.User
import com.example.playuser.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject internal constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private var _flowData = MutableStateFlow<List<User>>(emptyList())
    private val uiState = MutableStateFlow(UserListViewState())
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        // This doesn't handle multiple 'refreshing' tasks, don't use this
        viewModelScope.launch {
            // A fake 2 second 'refresh'
            _isRefreshing.emit(true)
            delay(2000)
            _isRefreshing.emit(false)
        }
    }

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
                // TODO
            }

            is UserListViewEvent.RemoveItem -> {
                val items = uiState.value.users.toMutableList().apply{
                    remove(viewEvent.user)
                    deleteUser(viewEvent.user)
                }.toList()

                uiState.value = uiState.value.copy(users = items)
            }

            is UserListViewEvent.UpCount -> {
/*
                val items = uiState.value.users.toMutableList()
                    .apply{viewEvent.user.count += 1}
                    .toList()
                uiState.value = uiState.value.copy(users = items)
*/

                val user = uiState.value.users.toMutableList()
                    .find{
                        it.userId == viewEvent.user.userId
                    }?.apply {count += 1}

                if (user != null) {
                    updateUser(user)
                    refresh()
                }
            }

            is UserListViewEvent.DownCount -> {
                val user = uiState.value.users.toMutableList()
                    .find{
                        it.userId == viewEvent.user.userId
                    }?.apply {if(count > 0) count -= 1}
                if (user != null) {
                    updateUser(user)
                    refresh()
                }
            }
        }
    }

    fun updateUser(user: User) = viewModelScope.launch {
        userRepository.updateUserDAO(user)
    }
    fun deleteUser(user: User) = viewModelScope.launch {
        userRepository.deleteUserDAO(user)
    }
}

data class UserListViewState(val isLoading: Boolean = true, val users: List<User> = emptyList())
sealed class UserListViewEvent{
    data class EditItem(val user: User): UserListViewEvent()
    data class RemoveItem(val user: User): UserListViewEvent()
    data class UpCount(val user: User): UserListViewEvent()
    data class DownCount(val user: User): UserListViewEvent()
}
