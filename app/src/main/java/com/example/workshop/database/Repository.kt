package com.example.workshop.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Repository(private val dao: Dao) {
    companion object{
        const val TAG = "Repository"
    }

    fun getAllWorkShops(): LiveData<List<WorkShopTable>>{
        Log.d(TAG, "getAllWorkShops -> SuccessFully fetched all workShops, size ${dao.getAllWorkShops().value?.size}")
        return dao.getAllWorkShops()
    }

    suspend fun addAllWorkShops(workShopTable: WorkShopTable){
        dao.addAllWorkShops(workShopTable)
    }

    suspend fun loginCurrentUser(emailId: String, password: String) =
        dao.loginUser(emailId, password)

    suspend fun signUpNewUser(userTable: UserTable){
        dao.signUpNewUser(userTable)
    }

    suspend fun enrollInWorkShop(enrollments: Enrollments){
        dao.applyForWorkShop(enrollments)
    }

    suspend fun unEnrollFromWorkShop(enrollments: Enrollments){
        dao.unEnrollFromworkShop(enrollments)
    }

    suspend fun fetchEnrolledWorkShops(userId: Int)
    : LiveData<List<WorkShopTable>> {
        val workShopList = dao.fetchEnrolledWorkShops(userId)
        if ( !workShopList.value.isNullOrEmpty()){
            Log.d(TAG, "fetchEnrolledWorkShops -> found ${workShopList.value!!.size}")
            return workShopList
        }
        Log.d(TAG, "fetchEnrolledWorkShops -> Not found")
        return workShopList
    }
}