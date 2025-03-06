package com.elpablo.fscslutsky

import android.app.Application
import com.vk.api.sdk.VK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        VK.initialize(this)
    }
}