package com.diusframi.feedinggood.data.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_login_entity")
data class UserLoginEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "date")
    val date: Long
) : Serializable
