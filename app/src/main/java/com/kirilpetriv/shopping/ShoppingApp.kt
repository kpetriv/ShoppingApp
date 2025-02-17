package com.kirilpetriv.shopping

import android.app.Application
import com.kirilpetriv.shopping.di.shoppingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ShoppingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ShoppingApp)
            modules(shoppingModule)
        }
    }
}