package com.example.workshop.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserTable(

    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    val userId: Int,

    @ColumnInfo(name= "user_name")
    val userName: String,

    @ColumnInfo(name = "email_id")
    val emailId: String,


    val password: String,
)