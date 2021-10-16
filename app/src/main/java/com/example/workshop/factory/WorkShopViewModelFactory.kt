package com.example.workshop.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workshop.viewmodel.WorkShopVeiwModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class WorkShopViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkShopVeiwModel::class.java))
            return WorkShopVeiwModel(app) as T
        throw IllegalArgumentException("Unable to instantiate WorkShopViewModel")
    }
}