package com.example.workshop.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.workshop.database.Dao
import com.example.workshop.database.MainDatabase
import com.example.workshop.database.Repository
import com.example.workshop.database.WorkShopTable
import com.example.workshop.fakedata.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkShopVeiwModel(application: Application): AndroidViewModel(application) {

    companion object{
        const val TAG = "WorkShopViewModel"
    }

    private lateinit var repo: Repository
    private var dao : Dao = MainDatabase.getDatabase(application).getDao()

    // WorkShopFragemnt
    var _allWorkShops = MutableLiveData<List<WorkShopTable>>()

    init {
        repo = Repository(dao)
    }

    fun fetchAllWorkshops(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "SuccessFully fetched all data")
            _allWorkShops = repo.getAllWorkShops()
        }
    }

    fun addAllWorkShops(){
        Log.d(TAG, "Adding all data into database")
        viewModelScope.launch(Dispatchers.IO) {
            val sampledata = SampleData.getSampleData()
            sampledata.forEach{
                repo.addAllWorkShops(it)
            }
        }
    }
}