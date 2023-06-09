package com.example.playuser.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy.Companion.IGNORE

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY id")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :idUser")
    fun getUser(idUser: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(RegisterUser: List<User>)

    @Insert(onConflict = IGNORE)
    suspend fun insert(item: User)

    @Update
    suspend fun update(item: User)

    @Delete
    suspend fun delete(item: User)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}