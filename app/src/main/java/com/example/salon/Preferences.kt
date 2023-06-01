package com.example.salon

import android.content.Context

class Preferences(context: Context) {
    val preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun saveSession(id: Int, username: String) {
        val preferences = preferences.edit()
        preferences.putInt("id", id)
        preferences.putString("username", username)
        preferences.apply()
    }

    fun getSession() = preferences.getInt("id", -0)
    fun getUsername() = preferences.getString("username", "")

    fun deleteSession() {
        preferences.edit().clear().apply()
    }
}