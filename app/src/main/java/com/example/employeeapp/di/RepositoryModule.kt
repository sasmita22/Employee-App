package com.example.employeeapp.di

import android.app.Application
import androidx.room.Room
import com.example.employeeapp.model.database.LocalDatabase
import com.example.employeeapp.repository.EmployeeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesEmployeeRepository(localDatabase: LocalDatabase): EmployeeRepository {
        return EmployeeRepository(localDatabase)
    }
}