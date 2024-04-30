package com.diusframi.feedinggood.data.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "food_entity")
data class FoodEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "is_vegetable")
    val isVegetable: Boolean,

    @ColumnInfo(name = "calories")
    var calories: String,

    @ColumnInfo(name = "carbohydrates")
    var carbohydrates: String,

    @ColumnInfo(name = "fat")
    var fat: String,

    @ColumnInfo(name = "proteins")
    var proteins: String,

    @ColumnInfo(name = "date")
    val date: Long
) : Serializable
