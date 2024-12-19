package com.grupo1.lojasocial

import android.app.Application
import androidx.room.Room
import com.grupo1.lojasocial.data.database.AppDatabase

class SocialStore : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "social_store_db"
        ).build()
    }
}
