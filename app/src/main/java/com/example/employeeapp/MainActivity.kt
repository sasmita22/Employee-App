package com.example.employeeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.employeeapp.DialogFormEmployee.OnCompletionCallback
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: EmployeeAdapterContract
    lateinit var dialog: DialogFormEmployee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar()
        setRecyclerView()
        setFormDialog()
        setViewModel()
    }

    private fun setFormDialog() {
        dialog = DialogFormEmployee(this,
            object : OnCompletionCallback {
                override fun onEdited(employee: Employee, position: Int) {
                    adapter.editData(employee, position)
                }

                override fun onCreated(employee: Employee) {
                    adapter.addData(employee)
                    setViewIsEmployeeEmpty(false)
                }
            })
    }

    private fun setViewModel() {
        /*val list = mutableListOf<Employee>().apply {
            for (i in 1..12){
                add(Employee(null, "Name"))
            }
        }
        adapter.updateData(list)
        setViewIsEmployeeEmpty(false)*/
    }

    private fun setRecyclerView() {
        adapter = EmployeeAdapter().also {
            recyclerview.adapter = it
            it.setCallback(object : EmployeeAdapter.Callback{
                override fun onDelete() {
                    GeneralSnackbar.showDeleteSuccess(rootView)
                }

                override fun onEdit(employee: Employee, position: Int) {
                    dialog.editEmployee(employee,position)
                }

            })
        }
        setViewIsEmployeeEmpty(true)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar.apply {
            title = "List of Employees"
        })
    }

    fun setViewIsEmployeeEmpty(isEmpty: Boolean){
        recyclerview.visibility = if (!isEmpty) View.VISIBLE else View.GONE
        emptyMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    fun setLoadingMode(isLoading: Boolean){
        if (isLoading) progressBar.visible() else progressBar.gone()
    }

    fun onViewClick(view: View) {
        when (view.id){
            fab.id -> {
                dialog.addEmployee()
            }
        }
    }


}