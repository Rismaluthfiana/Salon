package com.example.salon.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuthDao {
    @Insert
    suspend fun register(auth: Auth)

    @Query("SELECT EXISTS (SELECT * FROM auth WHERE username =:username)")
    suspend fun checkUsername(username: String): Boolean

    @Query("SELECT * FROM auth WHERE username =:username")
    suspend fun login(username: String): Auth
}