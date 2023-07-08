package com.danielys.a09_restaurantreview.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorit")
data class User(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    var username: String,

    @field:ColumnInfo(name = "url_img")
    var urlImg: String,
)