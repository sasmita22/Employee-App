package com.example.employeeapp.model

import com.example.employeeapp.util.ActionType
import java.lang.Exception

class ActionResult<T>(val type: ActionType){
    var result : T? = null
    var error: Exception? = null

    constructor(result : T, type: ActionType) : this(type){
        this.result = result
    }

    constructor(error: Exception, type: ActionType): this(type){
        this.error = error
    }
}