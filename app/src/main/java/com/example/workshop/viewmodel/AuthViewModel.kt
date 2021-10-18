package com.example.workshop.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
    var userId = MutableLiveData<Int>(-1)

    val sharedPref = getApplication<Application>().getSharedPreferences("LoginSignUp", Context.MODE_PRIVATE)

    init {
        repo = Repository(dao)
        userId.value = sharedPref.getInt("userId", 0)
    }

    fun getUserId(emailId: String, password: String, authListener: AuthListener?) {
        viewModelScope.launch {
            repo.getUserId(emailId, password, userId)

            if (userId.value!! > 0){
                authListener?.onAuthCompleted()
                sharedPref.edit().putInt("userId", userId.value!!).apply()
            }
            else{
                authListener?.onAuthFailed("Sorry, No Users Found")
            }
            Log.d(TAG, "getUsrId -> uid ${userId.value} username ")
        }
    }

    fun logOut(){
        // when logged out setting sharedPref userId to 0 as default value
        sharedPref.edit().putInt("userId", 0).apply()
        userId.value = sharedPref.getInt("userId", 0)
    }

    private fun validateLoginCred(): Boolean {
        when {
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

    fun loginUser(view: View) {
        if (validateLoginCred()) {
            getUserId(
                emailIdOrUserId.value!!,
                passwordEncrypter(password.value!!),
                authListener
            )
        }
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
                        passwordEncrypter(password.value!!)
                    ),
                    authListener
                )
                getUserId(
                    emailIdOrUserId.value!!,
                    passwordEncrypter(password.value!!),
                    authListener
                )
            }
        }
    }

    fun passwordEncrypter(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
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