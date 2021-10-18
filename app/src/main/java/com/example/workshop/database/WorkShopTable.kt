package com.example.workshop.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workshop_table")
data class WorkShopTable (
    @ColumnInfo(name = "workshop_id")
    @PrimaryKey(autoGenerate = true)
    val workShopId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "img_url")
    val imgUrl: String,

    @ColumnInfo(name = "web_link")
    val webLink: String
)