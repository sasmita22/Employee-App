package com.example.employeeapp.model.database

import androidx.room.*
import com.example.employeeapp.model.Employee
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface EmployeeDao {
    @Insert
    fun insert(employee: Employee): Completable

    @Query("select * from employee")
    fun retrieveEmployees() : Single<List<Employee>>

    @Update
    fun updateEmployee(employee: Employee) : Completable

    @Delete
    fun deleteEmployee(employee: Employee) : Completable
}