package com.example.playuser.data

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository  @Inject constructor(private val userDao: UserDao){
    init {

    }

    fun getUsers() = userDao.getUsers()

    fun getUser(userId: String) = userDao.getUser(userId)


    suspend fun addUserRepo(userItem: User) {
        userDao.insert(userItem)
    }

    suspend fun updateUserDAO(user: User) = userDao.update(user)

    suspend fun deleteUserDAO(user: User) = userDao.delete(user)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: UserRepository? = null

        fun getInstance(userDao: UserDao) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao).also { instance = it }
            }
    }
}