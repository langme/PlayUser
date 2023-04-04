package com.example.playuser.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "user" , indices = [Index(value = ["emailUser", "id"], unique = true)])
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var userId: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String = "",
    @ColumnInfo(name = "lastName")
    var lastName: String = "",
    @ColumnInfo(name = "emailUser")
    var emailUser: String ="",
    @ColumnInfo(name = "isvisible")
    var isVisible: Boolean = true,
    @ColumnInfo(name = "count")
    var count: Int = 0
){
    constructor(firstName: String, lastName: String, email: String) : this(firstName =  firstName, lastName = lastName, emailUser = email)
}