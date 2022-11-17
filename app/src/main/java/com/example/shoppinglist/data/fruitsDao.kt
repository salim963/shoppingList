package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface fruitsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFruits(fruits: fruits)

    @Query("SELECT * FROM FRUITS_TABLE ORDER BY id ASC")
    fun readAllData(): LiveData<List<fruits>>
}