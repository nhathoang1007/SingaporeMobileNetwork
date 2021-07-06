package com.example.koin.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.koin.extensions.applicationContext
import com.google.gson.Gson
import io.reactivex.Observable


class SharedPrefs constructor(private val gson: Gson){
    private val mSharedPreferences: SharedPreferences by lazy {
        applicationContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun <T> get(key: String, anonymousClass: Class<T>): Observable<T> {
        return Observable.create { emitter ->
            val sharedPreferencesListener =
                SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                    if (key == key) {
                        emitter.onNext(getTypeKey(key, anonymousClass, sharedPreferences))
                    }
                }
            emitter.setCancellable {
                mSharedPreferences.unregisterOnSharedPreferenceChangeListener(
                    sharedPreferencesListener
                )
            }
            emitter.onNext(getTypeKey(key, anonymousClass, mSharedPreferences))
            emitter.onComplete()
            mSharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
        }
    }

    fun <T> getTypeKey(key: String, anonymousClass: Class<T>, sharePref: SharedPreferences) : T {
        return when (anonymousClass) {
            String::class.java -> sharePref.getString(key, "") as T
            Boolean::class.java -> sharePref.getBoolean(key, false) as T
            Float::class.java -> sharePref.getFloat(key, 0f) as T
            Int::class.java -> sharePref.getInt(key, 0) as T
            Long::class.java -> sharePref.getLong(key, 0) as T
            else -> gson.fromJson(sharePref.getString(key, ""), anonymousClass)
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data as String)
            is Boolean -> editor.putBoolean(key, data as Boolean)
            is Float -> editor.putFloat(key, data as Float)
            is Int -> editor.putInt(key, data as Int)
            is Long -> editor.putLong(key, data as Long)
            else -> editor.putString(key, gson.toJson(data))
        }
        editor.apply()
    }
    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val SHARED_PREFERENCE_NAME = "SHARED_PREFERENCE_NAME"
    }
}
