
package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.attribute.UserPrincipal

class FruitsViewModel(application: Application):AndroidViewModel(application) {

    private val readAllData:LiveData<List<fruits>>
    private val repository: FruitsRepository
    init{
         val fruitsDao =FDataBase.getDataBase(application).fruitsDao()
        repository= FruitsRepository(fruitsDao)
        readAllData =repository.readAllData
    }

    fun addFruits(fruits: fruits){
        viewModelScope.launch ( Dispatchers.IO){
            repository.addFruits(fruits)
        }
    }
}

