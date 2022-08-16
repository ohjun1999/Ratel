package com.link.ratel

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private val MY_ACCOUNT: String = "account"
    private val MY_ACCOUNT1: String = "account1"
    private val EVENT_DATE: String = "eventDate"
    private val CHECK_BOX: String = "check"
    private val PHONE_NUM: String = "phoneN"
    private val USER_ID: String = "uid"

    fun setUserId(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT1, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.apply()
    }

    fun getUserId(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT1, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }

    fun setUserPass(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PASS", input)
        editor.apply()
    }

    fun getUserPass(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PASS", "").toString()
    }

    fun setUserUid(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_UID", input)
        editor.apply()
    }

    fun getUserUid(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        return prefs.getString("MY_UID", "").toString()
    }

    fun setCheckBox(context: Context, input: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(CHECK_BOX, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_BOOKMARK", input)
        editor.apply()
    }

    fun getPhoneNum(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PHONE_NUM, Context.MODE_PRIVATE)
        return prefs.getString("MY_PHONE_NUM", "").toString()
    }

    fun setPhoneNum(context: Context, input: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PHONE_NUM, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PHONE_NUM", input)
        editor.apply()
    }

    fun getCheckBox(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(CHECK_BOX, Context.MODE_PRIVATE)
        return prefs.getString("MY_BOOKMARK", "").toString()
    }

    fun setEventDate(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("EVENT_DATE", input)
        editor.apply()
    }


    fun clearUser(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}