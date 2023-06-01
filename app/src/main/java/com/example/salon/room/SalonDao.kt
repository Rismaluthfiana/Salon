package com.example.salon.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SalonDao {
    @Insert
    suspend fun addSalon(salon: Salon)

    @Query("SELECT * FROM salon")
    fun getAllSalon(): LiveData<List<Salon>>
}