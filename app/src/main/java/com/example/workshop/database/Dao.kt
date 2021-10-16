package com.example.workshop.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface Dao {

    @Query("SELECT * FROM workshop_table ORDER BY workshop_id ASC")
    suspend fun getAllWorkShops(): MutableLiveData<List<WorkShopTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllWorkShops(workShop: WorkShopTable)

    // SignUp
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun signUpNewUser(userTable: UserTable): Int

    // Login
    @Query("SELECT user_id FROM user_table WHERE email_id = :emailId AND password = :password")
    suspend fun loginUser(emailId: String, password: String) : Int

    // Apply for a specific workshop
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun applyForWorkShop(enrollments: Enrollments)

    // Un-enrolls from a workshop
    @Delete
    suspend fun unEnrollFromworkShop(enrollments: Enrollments)

    @Query("SELECT * FROM workshop_table INNER JOIN enrollments ON workshop_table.workshop_id = enrollments.workshop_id WHERE enrollments.user_id = :userId")
    suspend fun fetchEnrolledWorkShops(userId: Int): MutableLiveData<List<WorkShopTable>>


}