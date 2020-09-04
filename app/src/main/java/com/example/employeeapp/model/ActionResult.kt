package com.example.employeeapp.model
import com.example.employeeapp.util.ActionType
import java.lang.Exception

class ActionResult<T>(){
    var result : T? = null
    var error: Exception? = null
    var type: ActionType? = null

    constructor(result : T) : this(){
        this.result = result
    }

    constructor(error: Exception): this(){
        this.error = error
    }
}