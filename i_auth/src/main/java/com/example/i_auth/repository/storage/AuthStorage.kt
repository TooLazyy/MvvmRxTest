package com.example.i_auth.repository.storage

import android.annotation.SuppressLint
import android.content.SharedPreferences

private const val KEY_USER_LOGGED_IN = "key_user_logged_in"

@SuppressLint("ApplySharedPref")
class AuthStorage(
    private val preferences: SharedPreferences
) {

    var isUserLoggedIn: Boolean
        get() = preferences.getBoolean(KEY_USER_LOGGED_IN, false)
        set(value) {
            preferences
                .edit()
                .putBoolean(KEY_USER_LOGGED_IN, value)
                .commit()
        }
}