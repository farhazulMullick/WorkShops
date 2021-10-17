package com.example.workshop.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.workshop.database.Dao
import com.example.workshop.database.MainDatabase
import com.example.workshop.database.Repository
import com.example.workshop.database.UserTable
import com.example.workshop.listeners.AuthListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    companion object {
        const val TAG = "AuthViewModel"
    }

    private val repo: Repository
    private val dao by lazy {
        MainDatabase.getDatabase(app).getDao()
    }

    var userName = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var emailIdOrUserId = MutableLiveData<String>()

    var authListener: AuthListener? = null

    init {
        repo = Repository(dao)
    }

    private fun validateSignUpCredentials(): Boolean {
        when {
            userName.value.isNullOrEmpty() -> {
                authListener?.onAuthFailed("User name can't be empty")
                return false
            }

            emailIdOrUserId.value.isNullOrEmpty() -> {
                authListener?.onAuthFailed("EmailId or UserId cannot be empty")
                return false
            }

            password.value.isNullOrEmpty() -> {
                authListener?.onAuthFailed("Please enter proper password")
                return false
            }

            !password.value.isNullOrEmpty() -> {
                when {
                    password.value!!.length < 6 -> {
                        authListener?.onAuthFailed("Password cannot be less than 6 character")
                        Log.d(TAG, "validateSignUp -> password less than 6 char")
                        return false
                    }
                }
            }
        }
        return true
    }

    fun signUpNewUser(view: View) {
        if (validateSignUpCredentials()) {
            viewModelScope.launch(Dispatchers.IO) {
                Log.d(TAG, "signUp -> ${userName.value} ${password.value} ${emailIdOrUserId.value}")
                repo.signUpNewUser(
                    UserTable(
                        0,
                        userName.value!!,
                        emailIdOrUserId.value!!,
                        passwordEncrypter(password.value!!, "SHA-256")
                    ),
                    authListener
                )
            }


        }
    }

    fun passwordEncrypter(password: String, algoName: String): String {
        val bytes = MessageDigest.getInstance(algoName).digest(password.toByteArray())
        return toHex(bytes)
    }

    private fun toHex(bytes: ByteArray): String {
        Log.d(
            TAG, "HexCode -> ${
                bytes.joinToString("") {
                    "%02x".format(it)
                }
            }"
        )
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
}