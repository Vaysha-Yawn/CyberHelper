package com.example.test.data_base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .name("ss")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .compactOnLaunch()
            .build()

        Realm.setDefaultConfiguration(config)
    }
}