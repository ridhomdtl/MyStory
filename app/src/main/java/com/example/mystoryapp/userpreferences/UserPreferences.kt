package com.example.mystoryapp.userpreferences

import android.content.Context

internal class UserPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val TOKEN = "token"
        private const val USER_ID = "userid"
        private const val NAME = "name"
        private const val STATUS = "status"
    }

    private val userpreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun userLogin(token: String, userid: String, username: String){
        val editor = userpreferences.edit()
        editor.putBoolean(STATUS,true)
        editor.putString(TOKEN,token)
        editor.putString(USER_ID,userid)
        editor.putString(NAME,username)
        editor.apply()
    }

    fun userStatus(): Boolean{
        return userpreferences.getBoolean(STATUS,false)
    }

    fun userToken(): String?{
        return userpreferences.getString(TOKEN, "no token")
    }

    fun userLogout(){
        val editor = userpreferences.edit()
        editor.clear()
        editor.apply()
    }
}