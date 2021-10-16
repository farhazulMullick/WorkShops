package com.example.workshop.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Query("SELECT * FROM workshop_table ORDER BY workshop_id ASC")
    fun getAllWorkShops(): LiveData<List<WorkShopTable>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllWorkShops(workShop: WorkShopTable)

    // SignUp
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun signUpNewUser(userTable: UserTable)

    // Login
    @Query("SELECT user_id FROM user_table WHERE email_id = :emailId AND password = :password")
    fun loginUser(emailId: String, password: String): UserTable

    // Apply for a specific workshop
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun applyForWorkShop(enrollments: Enrollments)

    // Un-enrolls from a workshop
    @Delete
    suspend fun unEnrollFromworkShop(enrollments: Enrollments)

    @Query("SELECT * FROM workshop_table INNER JOIN enrollments ON workshop_table.workshop_id = enrollments.workshop_id WHERE enrollments.user_id = :userId")
    fun fetchEnrolledWorkShops(userId: Int): LiveData<List<WorkShopTable>>


}