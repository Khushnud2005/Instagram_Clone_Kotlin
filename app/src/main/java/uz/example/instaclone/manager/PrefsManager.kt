package uz.example.instaclone.manager

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {
    private var sharedPreference:SharedPreferences?

    companion object{
        private var prefsManager:PrefsManager? = null
        fun getInstance(context: Context):PrefsManager?{
            if (prefsManager==null){
                prefsManager = PrefsManager(context)
            }
            return prefsManager
        }
    }
    init {
        sharedPreference = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    fun saveData(key:String?,value:String?){
        val prefsEditor = sharedPreference!!.edit()
        prefsEditor.putString(key,value)
        prefsEditor.commit()
    }
    fun loadData(key:String?):String?{
        return if (sharedPreference !=null) sharedPreference!!.getString(key,"") else ""
    }
    fun clearAll() {
        val editor = sharedPreference!!.edit()
        editor.clear()
        editor.apply()
    }
}