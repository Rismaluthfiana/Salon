package com.example.salon.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Auth(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val password: String
)
