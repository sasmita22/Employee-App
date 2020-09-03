package com.example.employeeapp.repository

import com.example.employeeapp.model.Employee
import com.example.employeeapp.model.database.EmployeeDao
import com.example.employeeapp.model.database.LocalDatabase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class EmployeeRepository @Inject constructor(localDatabase: LocalDatabase) {
    private val employeeDao: EmployeeDao = localDatabase.employeeDao()

    fun retrieve(): Single<List<Employee>> {
        return employeeDao.retrieveEmployees()
            .subscribeOn(Schedulers.io())
    }

    fun insert(employee: Employee): Completable{
        return employeeDao.insert(employee)
            .subscribeOn(Schedulers.io())
    }

    fun update(employee: Employee): Completable{
        return employeeDao.updateEmployee(employee)
            .subscribeOn(Schedulers.io())
    }

    fun delete(employee: Employee): Completable{
        return employeeDao.deleteEmployee(employee)
            .subscribeOn(Schedulers.io())
    }
}