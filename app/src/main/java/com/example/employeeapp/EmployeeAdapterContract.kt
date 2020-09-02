package com.example.employeeapp

import java.text.FieldPosition

interface EmployeeAdapterContract {
    fun updateData(list: MutableList<Employee>)
    fun addData(employee: Employee)
    fun editData(employee: Employee, position: Int)
    fun setCallback(callback: EmployeeAdapter.Callback)
}