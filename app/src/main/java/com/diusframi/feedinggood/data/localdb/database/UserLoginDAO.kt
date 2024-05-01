package com.diusframi.feedinggood.data.localdb.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.diusframi.feedinggood.data.localdb.model.UserLoginEntity

@Dao
interface UserLoginDAO {

    @Query("select * from user_login_entity")
    fun getAllLiveData(): LiveData<List<UserLoginEntity>>?

    @Query("select * from user_login_entity")
    fun getAll(): List<UserLoginEntity>?

    @Query("select * from user_login_entity where id = :userLoginEntityId")
    suspend fun getById(userLoginEntityId: Int): UserLoginEntity?

    @Query("select * from user_login_entity where user_name = :userName")
    suspend fun getByUserName(userName: String): UserLoginEntity?

    @Query("select * from user_login_entity where user_name = :userName and password = :password")
    suspend fun getByUserNameAndPassword(userName: String, password:String): UserLoginEntity?

    @Insert
    suspend fun insert(userLoginEntity: UserLoginEntity)

    @Update
    suspend fun update(userLoginEntity: UserLoginEntity)

    @Delete
    suspend fun delete(userLoginEntity: UserLoginEntity)
}