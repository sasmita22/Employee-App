package com.example.employeeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_employee.view.*

class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>(), EmployeeAdapterContract{
    private var callback: Callback? = null
    private var list : MutableList<Employee> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employeeName =list[position].name
        holder.itemView.namaField.text = employeeName
        holder.itemView.moreMenu.setOnClickListener {
            showPopupMenu(it, position)
        }
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(view.context,view)
        popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.edit_menu -> {
                    callback?.onEdit(list[position],position)
                    return@setOnMenuItemClickListener true
                }
                R.id.delete_menu -> {
                    deleteData(position)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener true
            }
        }
        popupMenu.show()
    }

    private fun deleteData(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
        callback?.onDelete()
    }

    override fun updateData(list: MutableList<Employee>) {
        this.list = mutableListOf<Employee>().apply { addAll(list) }
    }

    override fun addData(employee: Employee) {
        this.list.add(employee)
    }

    override fun editData(employee: Employee, position: Int) {
        list[position] = employee
        notifyDataSetChanged()
    }

    override fun setCallback(callback: Callback) {
        this.callback = callback
    }

    interface Callback{
        fun onDelete()
        fun onEdit(employee: Employee, position: Int)
    }
}