package com.example.salon.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Insert
    suspend fun addReservation(reservation: Reservation)

    @Query("SELECT * FROM reservation WHERE iduser =:id")
    fun getAllReservation(id: Int?): LiveData<List<Reservation>>

    @Query("DELETE FROM reservation WHERE id =:id")
    suspend fun delete(id: Int?)
}