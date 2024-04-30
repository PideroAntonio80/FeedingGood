package com.diusframi.feedinggood.ui.login

import androidx.lifecycle.ViewModel
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.localdb.model.UserLoginEntity

class LoginViewModel : ViewModel() {

    suspend fun getUserVM(name: String, pass: String): UserLoginEntity? {

        return FeedingGoodDatabase.database.getUserLoginDao().getByUserNameAndPassword(name, pass)
    }
}