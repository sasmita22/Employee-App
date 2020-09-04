package com.example.employeeapp.view

import androidx.appcompat.app.AppCompatActivity
import com.example.employeeapp.model.ActionResult
import com.example.employeeapp.util.GeneralSnackbar

abstract class BaseActivity : AppCompatActivity(){

    fun <T> isResultContainsError(actionResult: ActionResult<T>): Boolean{
        if (actionResult.error != null){
            GeneralSnackbar.showError(window.decorView.rootView, actionResult.error!!.message!!)
            return true
        }
        return false
    }
}