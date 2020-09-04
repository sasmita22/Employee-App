package com.example.employeeapp.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.employeeapp.*
import com.example.employeeapp.model.Employee
import com.example.employeeapp.model.ActionResult
import com.example.employeeapp.util.ActionType
import com.example.employeeapp.util.GeneralSnackbar
import com.example.employeeapp.view.dialog.DialogFormEmployee.OnCompletionCallback
import com.example.employeeapp.view.adapter.EmployeeAdapter
import com.example.employeeapp.view.adapter.EmployeeAdapterContract
import com.example.employeeapp.view.dialog.DialogFormEmployee
import com.example.employeeapp.viewmodel.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var adapter: EmployeeAdapterContract
    private lateinit var dialog: DialogFormEmployee
    private lateinit var employeeViewModel: EmployeeViewModel
    private val recyclerViewCallback = object : EmployeeAdapter.Callback {
        override fun onDelete(employee: Employee) {
            employeeViewModel.deleteEmployee(employee)
        }

        override fun onEdit(employee: Employee, position: Int) {
            dialog.editEmployee(employee,position)
        }

        override fun isListEmpty(isEmpty: Boolean) {
            setViewIsEmployeeEmpty(isEmpty)
        }

    }
    private val dialogFormCallback = object : OnCompletionCallback {
        override fun result(employee: Employee, position: Int?) {
            if (position!=null){//edit
                adapter.editData(employee, position)
                employeeViewModel.updateEmployee(employee)
            }else{//add
                employeeViewModel.insertEmployee(employee)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar()
        setRecyclerView()
        setFormDialog()
        setViewModel()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar.apply {
            title = "List of Employees"
        })
    }

    private fun setRecyclerView() {
        adapter = EmployeeAdapter(
            recyclerViewCallback
        ).also {
            recyclerview.adapter = it
        }
        setViewIsEmployeeEmpty(true)
    }

    private fun setFormDialog() {
        dialog = DialogFormEmployee(
            this,
            dialogFormCallback
        )
    }

    private fun setViewModel() {
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        employeeViewModel.getCompletableLiveData()
            .observe(this,
                Observer<ActionResult<Boolean>> {
                    if (!isResultContainsError(it)){
                        setCompletableData(it)
                    }
                })

        employeeViewModel.getEmployeeListLiveData()
            .observe(this,
                Observer<ActionResult<List<Employee>>> {
                    if (!isResultContainsError(it)){
                        adapter.updateData(it.result!!.toMutableList())
                    }
                })

        employeeViewModel.getEmployeeInsertedLiveData()
            .observe(this,
                Observer<ActionResult<Employee>>{
                    if (!isResultContainsError(it)){
                        setViewIsEmployeeEmpty(false)
                        adapter.addData(it.result!!)
                        GeneralSnackbar.showAdditionSuccess(rootView)
                    }
                })
    }

    private fun setCompletableData(actionResult: ActionResult<Boolean>) {
        if (actionResult.type != null){
            when (actionResult.type) {
                ActionType.UPDATE -> {
                    GeneralSnackbar.showEditSuccess(rootView)
                }
                ActionType.DELETE -> {
                    GeneralSnackbar.showDeleteSuccess(rootView)
                }
            }
        }
    }

    fun setViewIsEmployeeEmpty(isEmpty: Boolean){
        recyclerview.visibility = if (!isEmpty) View.VISIBLE else View.GONE
        emptyMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    fun onViewClick(view: View) {
        when (view.id){
            fab.id -> {
                dialog.addEmployee()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        employeeViewModel.retrieveEmployee()
    }
}