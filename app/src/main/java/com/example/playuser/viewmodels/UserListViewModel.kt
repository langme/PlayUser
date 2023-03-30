package com.example.playuser.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playuser.data.User
import com.example.playuser.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject internal constructor(
    userRepository: UserRepository,
) : ViewModel() {

    private val _liveData = MutableLiveData<Flow<List<User>>>()
    val users: LiveData<List<User>> = userRepository.getUsers().asLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(userRepository.getUsers())
        }
    }
}