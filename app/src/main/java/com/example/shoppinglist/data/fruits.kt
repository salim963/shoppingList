package com.example.shoppinglist.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fruits_table")
data class fruits(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val icon: String,
    val name:String,
    val button:String)