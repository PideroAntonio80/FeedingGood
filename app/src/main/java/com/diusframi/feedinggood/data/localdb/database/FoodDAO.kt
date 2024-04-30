package com.diusframi.feedinggood.data.localdb.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.diusframi.feedinggood.data.localdb.model.FoodEntity

@Dao
interface FoodDAO {

    @Query("select * from food_entity")
    fun getAll(): LiveData<List<FoodEntity>>?

    @Query("DELETE FROM food_entity")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(foodEntity: FoodEntity)

    @Update
    suspend fun update(foodEntity: FoodEntity)

    @Delete
    suspend fun delete(foodEntity: FoodEntity)
}