package com.diusframi.feedinggood.ui.foodList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodListViewModel : ViewModel() {

    fun getFoodListVM(): LiveData<List<FoodEntity>>? {
        return FeedingGoodDatabase.database.getFoodDao().getAll()
    }

    fun deleteAllFoodVM() {
        viewModelScope.launch(Dispatchers.IO) {

            FeedingGoodDatabase.database.getFoodDao().deleteAll()
        }
    }

    fun deleteFoodVM(foodEntity: FoodEntity) {
        viewModelScope.launch(Dispatchers.IO) {

            FeedingGoodDatabase.database.getFoodDao().delete(foodEntity)
        }
    }
}