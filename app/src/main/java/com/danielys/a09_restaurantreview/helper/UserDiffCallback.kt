package com.danielys.a09_restaurantreview.helper

import androidx.recyclerview.widget.DiffUtil
import com.danielys.a09_restaurantreview.database.User

class UserDiffCallback(private val mOldNoteList: List<User>, private val mNewNoteList: List<User>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }
    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].username == mNewNoteList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]
        return oldEmployee.username == newEmployee.username && oldEmployee.urlImg == newEmployee.urlImg
    }
}