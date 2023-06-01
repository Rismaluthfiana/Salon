package com.example.salon.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val iduser: Int,
    val name: String,
    val time: String,
    val salonName: String,
    val people: Int,
    val salonImg: String
)