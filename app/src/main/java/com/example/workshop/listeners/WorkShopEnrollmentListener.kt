package com.example.workshop.listeners

interface WorkShopEnrollmentListener {
    fun onApplied(message: String)
    fun onApplyFailed(message: String)
}