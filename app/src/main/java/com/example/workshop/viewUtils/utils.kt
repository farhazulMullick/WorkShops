package com.example.workshop.viewUtils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.customChrometab(url: String) =
    CustomTabsIntent.Builder()
        .build()
        .launchUrl(this, Uri.parse(url))