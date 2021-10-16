package com.example.workshop.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workshop.viewmodel.AuthViewModel

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java))
            return AuthViewModel(app) as T
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}