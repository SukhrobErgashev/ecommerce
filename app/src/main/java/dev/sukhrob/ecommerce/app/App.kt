package dev.sukhrob.ecommerce.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    companion object {
        const val TAG = "Bismillah"
    }
}