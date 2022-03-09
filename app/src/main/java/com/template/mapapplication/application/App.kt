package com.template.mapapplication.application

import android.app.Application
import com.template.mapapplication.KeyClass
import com.template.mapapplication.di.appModule
import com.template.mapapplication.di.dataModule
import com.template.mapapplication.di.domainModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            /* При импорте KPermissions(даже без использования, просто импорт),приложение билдится,
                 но вылетает при запуске. Замена Debug на Error это чинит. Не понимаю почему
             */
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }

        //Чтоб не заливать ключ на гит
        MapKitFactory.setApiKey(KeyClass().MapKey)
    }
}