package com.example.employeeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_form_employee.view.*
import java.text.FieldPosition

class DialogFormEmployee(private val context: Context, private val callback: OnCompletionCallback) {
    private var isEditMode = false
    private var positionEmployee: Int? = null
    private var dialog: AlertDialog
    private val view = LayoutInflater.from(context)
        .inflate(R.layout.dialog_form_employee,null)

    init {

        dialog = AlertDialog.Builder(context)
            .setView(view)
            .create()

        view.formSubmit.setOnClickListener {
            if (!view.formField.text.isNullOrEmpty()){
                val newEmployee = Employee(null,view.formField.text.toString())
                if (isEditMode && positionEmployee != null){
                    callback.onEdited(newEmployee, positionEmployee!!)
                }else{
                    callback.onCreated(newEmployee)
                }
                dialog.dismiss()
            }else{
                Toast.makeText(context, "Employee name must be filled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun editEmployee(employee: Employee, positionEmployee: Int){
        isEditMode = true
        clearField()
        this.positionEmployee = positionEmployee
        view.formField.setText(employee.name)
        view.formSubmit.text = context.resources.getString(R.string.edit_button_text)
        dialog.show()
    }

    fun addEmployee(){
        isEditMode = false
        clearField()
        view.formSubmit.text = context.resources.getString(R.string.add_button_text)
        dialog.show()
    }

    fun clearField(){
        view.formField.setText("")
    }

    interface OnCompletionCallback{
        fun onEdited(employee: Employee, position: Int)
        fun onCreated(employee: Employee)
    }
}