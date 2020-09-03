package com.example.employeeapp.view.adapter

import com.example.employeeapp.model.Employee

interface EmployeeAdapterContract {
    fun updateData(list: MutableList<Employee>)
    fun addData(employee: Employee)
    fun editData(employee: Employee, position: Int)
}