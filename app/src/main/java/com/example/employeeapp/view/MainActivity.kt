package com.example.employeeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.employeeapp.*
import com.example.employeeapp.model.Employee
import com.example.employeeapp.model.ActionResult
import com.example.employeeapp.util.ActionType
import com.example.employeeapp.util.GeneralSnackbar
import com.example.employeeapp.util.gone
import com.example.employeeapp.util.visible
import com.example.employeeapp.view.dialog.DialogFormEmployee.OnCompletionCallback
import com.example.employeeapp.view.adapter.EmployeeAdapter
import com.example.employeeapp.view.adapter.EmployeeAdapterContract
import com.example.employeeapp.view.dialog.DialogFormEmployee
import com.example.employeeapp.viewmodel.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
                adapter.addData(employee)
                setViewIsEmployeeEmpty(false)
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

    override fun onResume() {
        super.onResume()
        employeeViewModel.retrieveEmployee()
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
                    if (it.result != null){
                        setCompletableData(it)
                    }else{
                        GeneralSnackbar.showError(rootView,it.error!!.message!!)
                    }
                })

        employeeViewModel.getEmployeeListLiveData()
            .observe(this,
                Observer<ActionResult<List<Employee>>> {
                    if (it.result != null){
                        adapter.updateData(it.result!!.toMutableList())
                    }else{
                        GeneralSnackbar.showError(rootView,it.error!!.message!!)
                    }
                })
    }

    private fun setCompletableData(actionResult: ActionResult<Boolean>) {
        if (actionResult.result != null){
            when (actionResult.type) {
                ActionType.INSERT -> {
                    GeneralSnackbar.showAdditionSuccess(rootView)
                }
                ActionType.UPDATE -> {
                    GeneralSnackbar.showEditSuccess(rootView)
                }
                ActionType.DELETE -> {
                    GeneralSnackbar.showDeleteSuccess(rootView)
                }
                else -> {
                    //nothing to_do
                }
            }
        }
    }

    private fun setRecyclerView() {
        adapter = EmployeeAdapter(
            recyclerViewCallback
        ).also {
            recyclerview.adapter = it
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