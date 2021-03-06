package com.example.workshop.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM workshop_table ORDER BY workshop_id ASC")
    fun getAllWorkShops(): LiveData<List<WorkShopTable>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllWorkShops(workShop: WorkShopTable)

    // SignUp
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun signUpNewUser(userTable: UserTable)

    // Login
    @Query("SELECT user_id FROM user_table WHERE email_id = :emailId AND password = :password")
    fun loginUser(emailId: String, password: String): Int

    // Apply for a specific workshop
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun applyForWorkShop(enrollments: Enrollments)

    // Un-enrolls from a workshop
    @Query("DELETE FROM enrollments WHERE user_id = :userId AND workshop_id = :workShopId")
    suspend fun unEnrollFromworkShop(userId: Int, workShopId: Int)

    @Query("SELECT EXISTS (SELECT * FROM enrollments WHERE user_id= :userId AND workshop_id = :workShopId)")
    suspend fun checkIfAlreadyEnrolled(userId: Int, workShopId: Int): Int

    @Query("SELECT * FROM workshop_table INNER JOIN enrollments ON workshop_table.workshop_id = enrollments.workshop_id WHERE enrollments.user_id = :userId")
    fun fetchEnrolledWorkShops(userId: Int): LiveData<List<WorkShopTable>>

    @Query("SELECT * FROM workshop_table INNER JOIN enrollments ON workshop_table.workshop_id = enrollments.workshop_id WHERE enrollments.user_id = :userId")
    fun fetchAppliedWorkShops(userId: Int): Flow<List<WorkShopTable>>

    @Query("SELECT user_name FROM user_table WHERE user_id = :userId")
    suspend fun getUserInfo(userId: Int): String



}