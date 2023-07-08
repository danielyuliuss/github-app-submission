package com.danielys.a09_restaurantreview.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM favorit")
    fun getFavorit(): LiveData<List<User>>

    @Query("SELECT EXISTS(SELECT * FROM favorit WHERE username = :username)")
    fun isFavorited(username: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorit(user: User)

    @Delete()
    fun deleteFavorit(user: User)

}