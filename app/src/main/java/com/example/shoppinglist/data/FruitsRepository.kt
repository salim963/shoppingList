package com.example.shoppinglist.data

import androidx.lifecycle.LiveData


class FruitsRepository(private val fruitsDao: fruitsDao) {
    val readAllData:LiveData<List<fruits>> = fruitsDao.readAllData()

    suspend fun addFruits(fruits: fruits){
        fruitsDao.addFruits(fruits)
    }


}