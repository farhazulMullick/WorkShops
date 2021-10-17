package com.example.workshop.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.workshop.database.*
import com.example.workshop.fakedata.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WorkShopVeiwModel(application: Application): AndroidViewModel(application) {

    companion object{
        const val TAG = "WorkShopViewModel"
    }

    private var repo: Repository
    private val dao: Dao by lazy {
        MainDatabase.getDatabase(application).getDao()
    }

    // WorkShopFragemnt
    val _allWorkShops: LiveData<List<WorkShopTable>>
    val _enrolledWorkShops: LiveData<List<WorkShopTable>>
    var sharedPref : SharedPreferences
    var userIdSharedPred: SharedPreferences
    var isDataAddedToDatabase: Boolean
    var userId = MutableLiveData<Int>(0)
    var userName = MutableLiveData<String>()

    init {
        repo = Repository(dao)
        _allWorkShops = repo.getAllWorkShops()
        userIdSharedPred = getApplication<Application>().getSharedPreferences("LoginSignUp", Context.MODE_PRIVATE)
        _enrolledWorkShops = repo.fetchEnrolledWorkShops(userIdSharedPred.getInt("userId", 0))
        sharedPref = getApplication<Application>().getSharedPreferences("WorkShopSp", Context.MODE_PRIVATE)
        isDataAddedToDatabase = sharedPref.getBoolean("isDataAdded", false)
    }

    fun fetchAllWorkshops(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "SuccessFully fetched all data")
            delay(3000)
            //_allWorkShops = repo.getAllWorkShops()
        }
    }

    fun addAllWorkShops(){
        Log.d(TAG, "Adding all data into database")
        viewModelScope.launch(Dispatchers.IO) {
            if ( !isDataAddedToDatabase){
                val sampledata = SampleData.getSampleData()
                sampledata.forEach {
                    Log.d(TAG, "Adding all data into database name ${it.title}")
                    repo.addAllWorkShops(it)
                }
                isDataAddedToDatabase = true
                sharedPref.edit().putBoolean("isDataAdded", isDataAddedToDatabase).apply()
            }
            else{
                Log.d(TAG, "addAllWorkShops-> Already added")
            }
        }
    }

    fun getUserId(): Int{
        userId.value = userIdSharedPred.getInt("userId", 0)
        Log.d(TAG, "getUserId -> ${userId.value}")
        return userId.value!!
    }

    fun enrollInWorkShop(workshopId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Applied to work shop")
            repo.enrollInWorkShop(
                Enrollments(
                    0,
                    userId.value!!,
                    workshopId
                )
            )
        }
    }

    fun fetchEnrolledWorkshops(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchEnrolledWorkShops(userId.value!!)
        }
    }

    fun getUserInfo(){
        viewModelScope.launch {
            repo.getUserInfo(userId.value!!, userName)
        }
    }

    fun logOut(){
        userIdSharedPred.edit().putInt("userId", 0).apply()
        userId.value = 0
    }
}