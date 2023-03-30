package com.example.playuser.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY id")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :idUser")
    fun getUser(idUser: String): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(RegisterUser: List<User>)

    @Insert
    suspend fun insert(item: User)

    @Update
    suspend fun update(item: User)

    @Delete
    suspend fun delete(item: User)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}