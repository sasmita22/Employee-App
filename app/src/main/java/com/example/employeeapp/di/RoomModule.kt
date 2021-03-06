package com.example.employeeapp.di

import android.app.Application
import androidx.room.Room
import com.example.employeeapp.model.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(application: Application):LocalDatabase{
        return Room.databaseBuilder<LocalDatabase>(
            application,
            LocalDatabase::class.java,
            "employeedatabase"
        ).build()
    }
}