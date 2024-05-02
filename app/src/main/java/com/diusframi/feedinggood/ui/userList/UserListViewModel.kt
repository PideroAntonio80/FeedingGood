package com.diusframi.feedinggood.ui.userList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.localdb.model.UserLoginEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    fun getUserListVM(): LiveData<List<UserLoginEntity>>? {
        return FeedingGoodDatabase.database.getUserLoginDao().getAllLiveData()
    }

    fun deleteAllUsersVM() {
        viewModelScope.launch(Dispatchers.IO) {

            FeedingGoodDatabase.database.getUserLoginDao().deleteAll()
        }
    }

    fun deleteUserVM(userLoginEntity: UserLoginEntity) {
        viewModelScope.launch(Dispatchers.IO) {

            FeedingGoodDatabase.database.getUserLoginDao().delete(userLoginEntity)
        }
    }
}