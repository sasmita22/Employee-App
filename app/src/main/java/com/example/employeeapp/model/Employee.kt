package com.example.employeeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee (
    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    var name : String?
)