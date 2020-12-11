package com.example.online_bank.repository.storage

import android.content.Context
import com.example.online_bank.App

class AuthorizationKey(private val myContext: Context) {
    private companion object {
        const val FILE_NAME = "container"
        const val TOKEN_KEY = "com.example.online_loans.token_key"
    }

    fun getKey(): String? {
        return myContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            .getString(TOKEN_KEY, "")
    }

    fun setKey(key: String) {
        myContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(TOKEN_KEY, key)
            .apply()
    }
}