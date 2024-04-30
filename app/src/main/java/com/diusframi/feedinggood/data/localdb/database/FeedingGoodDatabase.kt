package com.diusframi.feedinggood.data.localdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import com.diusframi.feedinggood.data.localdb.model.UserLoginEntity
import com.diusframi.feedinggood.utils.APP_DB

@Database(entities = [FoodEntity::class, UserLoginEntity::class], version = 1, exportSchema = false)
abstract class FeedingGoodDatabase: RoomDatabase() {

    companion object {
        lateinit var database: FeedingGoodDatabase

        fun createDb(context: Context) {
            database = Room.databaseBuilder(context, FeedingGoodDatabase::class.java, APP_DB).build()
        }
    }

    abstract fun getFoodDao(): FoodDAO
    abstract fun getUserLoginDao(): UserLoginDAO
}