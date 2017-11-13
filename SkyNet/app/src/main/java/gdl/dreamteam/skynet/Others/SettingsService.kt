package gdl.dreamteam.skynet.Others

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by migue on 05/11/2017.
 */
class SettingsService(context : Context){
    private val APP_SHARED_PREFS : String = SettingsService::class.java.simpleName
    private val sharedPrefs:SharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE)
    private val prefEditor : SharedPreferences.Editor = sharedPrefs.edit()

    fun saveString(key : String, value: String){
        prefEditor.putString(key, value)
        prefEditor.commit()
    }

    fun getString(key : String) : String
            = sharedPrefs.getString(key, "")
}