package com.example.salon.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Salon(
    @PrimaryKey
    val id: Int,
    val salonName: String,
    val salonImg: String,
    val salonAddress: String,
    val salonPhone: String,
    val salonOpenTime: String,
    val salonDesc: String
) : Serializable
