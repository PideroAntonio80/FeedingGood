package com.diusframi.feedinggood.ui.foodDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.localdb.model.FoodEntity

class FoodDetailViewModel : ViewModel() {

    fun getFoodListVM(id: Int): LiveData<FoodEntity>? {
        return FeedingGoodDatabase.database.getFoodDao().getByIdDetail(id)
    }
}