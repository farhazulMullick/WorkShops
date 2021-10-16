package com.example.workshop.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "enrollments")
data class Enrollments(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "enrollment_id")
    val enrollmentId: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "workshop_id")
    val workShopId: Int,

)
