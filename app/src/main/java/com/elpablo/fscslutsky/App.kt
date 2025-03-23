package com.elpablo.fscslutsky

import android.app.Application
import android.util.Log
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.vk.id.VKID
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        VK.initialize(this)
        VKID.init(this)
        VKID.instance.setLocale(Locale("ru"))
        VK.addTokenExpiredHandler(tokenTracker)
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            Log.d("VKID", "Token expaired")
        }
    }
}