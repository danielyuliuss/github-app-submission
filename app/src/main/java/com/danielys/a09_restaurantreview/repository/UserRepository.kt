package com.danielys.a09_restaurantreview.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.danielys.a09_restaurantreview.database.User
import com.danielys.a09_restaurantreview.database.UserDao
import com.danielys.a09_restaurantreview.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val userDao : UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        userDao = db.userDao()
    }

    fun getAllFavorit(): LiveData<List<User>> = userDao.getFavorit()
    fun isFavorit(username: String): LiveData<Boolean> = userDao.isFavorited(username)
    fun insert(user: User) {
        executorService.execute { userDao.insertFavorit(user) }
    }
    fun delete(user: User) {
        executorService.execute { userDao.deleteFavorit(user) }
    }


}