package com.example.playuser.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.playuser.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
) : ViewModel() {
    val userId: String = savedStateHandle.get<String>(USER_ID_SAVED_STATE_KEY)!!
    val user = userRepository.getUser(userId).asLiveData()

    init{

    }

    companion object {
        private const val USER_ID_SAVED_STATE_KEY = "userId"
    }
}