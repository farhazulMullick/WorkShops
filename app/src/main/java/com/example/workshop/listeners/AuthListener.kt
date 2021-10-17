package com.example.workshop.listeners

interface AuthListener {
    fun onAuthStarted()
    fun onAuthFailed(message: String)
    fun onAuthCompleted()
}