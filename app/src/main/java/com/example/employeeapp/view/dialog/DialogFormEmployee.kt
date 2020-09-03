package com.example.employeeapp.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.employeeapp.model.Employee
import com.example.employeeapp.R
import kotlinx.android.synthetic.main.dialog_form_employee.view.*

class DialogFormEmployee(private val context: Context, private val callback: OnCompletionCallback) {
    private var employee: Employee? = null
    private var isEditMode = false
    private var positionEmployee: Int? = null
    private var dialog: AlertDialog
    private val view = LayoutInflater.from(context)
        .inflate(R.layout.dialog_form_employee, null)

    init {

        dialog = AlertDialog.Builder(context)
            .setView(view)
            .create()

        view.formSubmit.setOnClickListener {
            if (!view.formField.text.isNullOrEmpty()) {
                val newEmployee = Employee(
                    employee?.id,
                    view.formField.text.toString()
                )

                callback.result(newEmployee, positionEmployee)
                setEverythingNull()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Employee name must be filled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setEverythingNull() {
        positionEmployee = null
        employee = null
    }

    fun editEmployee(employee: Employee, positionEmployee: Int) {
        isEditMode = true
        clearField()
        this.positionEmployee = positionEmployee
        this.employee = employee
        view.formField.setText(employee.name)
        view.formSubmit.text = context.resources.getString(R.string.edit_button_text)
        dialog.show()
    }

    fun addEmployee() {
        isEditMode = false
        clearField()
        view.formSubmit.text = context.resources.getString(R.string.add_button_text)
        dialog.show()
    }

    private fun clearField() {
        view.formField.setText("")
    }

    interface OnCompletionCallback {
        fun result(employee: Employee, position: Int?)
    }
}