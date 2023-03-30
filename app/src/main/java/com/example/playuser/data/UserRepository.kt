package com.example.playuser.data

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository  @Inject constructor(private val userDao: UserDao){

    fun getUsers() = userDao.getUsers()

    fun getUser(userId: String) = userDao.getUser(userId)


    init {

    }

    suspend fun addUserRepo(userItem: User) {
        userDao.insert(userItem)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: UserRepository? = null

        fun getInstance(userDao: UserDao) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao).also { instance = it }
            }
    }
}