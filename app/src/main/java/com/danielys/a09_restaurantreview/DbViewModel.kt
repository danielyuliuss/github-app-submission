package com.danielys.a09_restaurantreview

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danielys.a09_restaurantreview.database.User
import com.danielys.a09_restaurantreview.repository.UserRepository

class DbViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)
    fun getAllFavorite(): LiveData<List<User>> = mUserRepository.getAllFavorit()

    fun insert(user: User) {
        mUserRepository.insert(user)
    }
    fun isFavorit(username: String): LiveData<Boolean> = mUserRepository.isFavorit(username)

    fun delete(user: User) {
        mUserRepository.delete(user)
    }
}