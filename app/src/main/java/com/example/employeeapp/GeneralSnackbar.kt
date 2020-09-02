package com.example.employeeapp

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class GeneralSnackbar {
    companion object {

        fun showAdditionSuccess(view: View){
            Snackbar.make(view, "Employee added successfully", Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(view.context, R.color.snackbarAddSuccess))
                .show()
        }

        fun showEditSuccess(view: View){
            Snackbar.make(view, "Employee edited successfully", Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(view.context, R.color.snackbarAddSuccess))
                .show()
        }

        fun showDeleteSuccess(view: View){
            Snackbar.make(view, "Employee edited successfully", Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(view.context, R.color.snackbarAddSuccess))
                .show()
        }
    }
}