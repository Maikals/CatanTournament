package com.example.catantournament

import android.app.Application
import com.example.catantournament.di.modulesList
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatanTournamentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CatanTournamentApplication)
            modules(modulesList)
        }
        Realm.init(this)
    }
}