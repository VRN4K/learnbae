package com.learnbae.my.data.storage.preferences

import android.content.SharedPreferences

class TokenPreference(sharedPreferences: SharedPreferences) :
    StringPreference(sharedPreferences, "token")