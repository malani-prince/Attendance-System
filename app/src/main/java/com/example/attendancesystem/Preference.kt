package com.example.attendancesystem

import android.content.*
import com.example.attendancesystem.Constants.Preference.PREFERENCE_NAME
import com.example.attendancesystem.Constants.Preference.VERSION

class Preference(private val context: Context?) {
    
    val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
    
    //SET STRING PREFERENCE
    fun setStringPreference(key: String?, value: String?) {
        editor?.putString(key, value)
        editor?.commit()
        editor?.apply()
    }
    
    //SET BOOLEAN PREFERENCE
    fun setBooleanPreference(key: String?, value: Boolean) {
        editor?.putBoolean(key, value)
        editor?.commit()
        editor?.apply()
    }
    
    //GET STRING PREFERENCE
    fun getStringPreference(key: String?): String {
        return sharedPreferences?.getString(key, "").toString()
    }
    
    //GET BOOLEAN PREFERENCE
    fun getBooleanPreference(key: String?, default: Boolean): Boolean? {
        return sharedPreferences?.getBoolean(key, default)
    }
    
    fun getVersionPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(VERSION, Context.MODE_PRIVATE)
    }
    
    //REMOVE PREFERENCE BY KEY
    fun removePreference(preferenceName: String) {
        editor?.remove(preferenceName)
        editor?.commit()
        editor?.apply()
    }
    
    fun removePreferenceFile() {
        context?.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)?.edit()?.clear()?.apply()
    }
    
}