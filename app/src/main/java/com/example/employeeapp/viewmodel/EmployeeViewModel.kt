package com.example.employeeapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employeeapp.model.Employee
import com.example.employeeapp.model.ActionResult
import com.example.employeeapp.repository.EmployeeRepository
import com.example.employeeapp.util.ActionType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

class EmployeeViewModel @ViewModelInject constructor(private val employeeRepository: EmployeeRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val employeeListLiveData = MutableLiveData<ActionResult<List<Employee>>>()
    private val employeeInsertedLiveData = MutableLiveData<ActionResult<Employee>>()
    private val completableLiveData = MutableLiveData<ActionResult<Boolean>>()

    fun getEmployeeListLiveData(): MutableLiveData<ActionResult<List<Employee>>>{
        return employeeListLiveData
    }

    fun getEmployeeInsertedLiveData(): MutableLiveData<ActionResult<Employee>>{
        return employeeInsertedLiveData
    }

    fun getCompletableLiveData(): MutableLiveData<ActionResult<Boolean>>{
        return completableLiveData
    }

    fun retrieveEmployee(){
        compositeDisposable.add(
            employeeRepository.retrieve()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    employeeListLiveData.value = ActionResult(it)
                }, {
                    employeeListLiveData.value = ActionResult(Exception(it))
                })
        )
    }

    fun insertEmployee(employee: Employee){
        compositeDisposable.add(
            employeeRepository.insert(employee)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    employeeInsertedLiveData.value = ActionResult(it)
                }, {
                    completableLiveData.value = ActionResult(Exception(it))
                })
        )
    }

    fun updateEmployee(employee: Employee){
        compositeDisposable.add(
            employeeRepository.update(employee)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    completableLiveData.value = ActionResult(true).apply { type = ActionType.UPDATE }
                }, {
                    completableLiveData.value = ActionResult<Boolean>(Exception(it)).apply {  type = ActionType.UPDATE }
                })
        )
    }

    fun deleteEmployee(employee: Employee){
        compositeDisposable.add(
            employeeRepository.delete(employee)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    completableLiveData.value = ActionResult(true).apply { type = ActionType.DELETE }
                }, {
                    completableLiveData.value = ActionResult<Boolean>(Exception(it)).apply {  type = ActionType.DELETE }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}