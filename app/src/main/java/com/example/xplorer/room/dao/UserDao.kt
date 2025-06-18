package com.example.xplorer.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.xplorer.room.entities.User

@Dao
interface UserDao {
    @Query("SELECT password FROM users where email = :email")
    fun getUserCredential(email : String): LiveData<String>

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}


