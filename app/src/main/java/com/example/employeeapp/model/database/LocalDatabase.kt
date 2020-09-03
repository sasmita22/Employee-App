package com.example.employeeapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.employeeapp.model.Employee

@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao
}