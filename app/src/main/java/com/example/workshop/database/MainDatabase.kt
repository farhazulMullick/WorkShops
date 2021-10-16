package com.example.workshop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WorkShopTable::class, Enrollments::class, UserTable::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase(){
    abstract fun getDao(): Dao
    companion object{
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_database"
                ).build()

                INSTANCE!!
            }
        }
    }
}