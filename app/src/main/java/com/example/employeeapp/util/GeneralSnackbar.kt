package com.example.employeeapp.util

import android.view.View
import androidx.core.content.ContextCompat
import com.example.employeeapp.R
import com.google.android.material.snackbar.Snackbar

class GeneralSnackbar {
    companion object {

        fun showAdditionSuccess(view: View){
            Snackbar.make(view, "Employee added successfully", Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(view.context,
                    R.color.snackbarAddSuccess
                ))
                .show()
        }

        fun showEditSuccess(view: View){
            Snackbar.make(view, "Employee edited successfully", Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(view.context,
                    R.color.snackbarEditSuccess
                ))
                .show()
        }

        fun showDeleteSuccess(view: View){
            Snackbar.make(view, "Employee deleted successfully", Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(view.context,
                    R.color.snackbarDeleteSuccess
                ))
                .show()
        }

        fun showError(view: View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(view.context,
                    R.color.snackbarError
                ))
                .show()
        }
    }
}