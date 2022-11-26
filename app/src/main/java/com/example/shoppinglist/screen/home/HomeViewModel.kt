package com.example.shoppinglist.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Notes
import com.example.shoppinglist.repository.MyStrogeRepository
import com.example.shoppinglist.repository.Resources
import com.example.shoppinglist.ui.theme.white
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MyStrogeRepository = MyStrogeRepository(),
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())

    val user = repository.user()
    val hasUser: Boolean
        get() = repository.hasUser()
    private val userId: String
        get() = repository.getUserId()

    fun loadNotes(){
        if (hasUser){
            if (userId.isNotBlank()){
                getUserNotes(userId)
            }

        }else{
            homeUiState = homeUiState.copy(notesList = Resources.Error(
                throwable = Throwable(message = "User is not Login")
            ))
        }
    }

    private fun getUserNotes(userId:String) = viewModelScope.launch {
        repository.getUserNotes(userId).collect {
            homeUiState = homeUiState.copy(notesList = it)
        }

    }

    fun deleteNote(noteId:String) = repository.deleteNote(noteId){
        homeUiState = homeUiState.copy(noteDeletedStatus = it)
    }



    fun signOut() = repository.signOut()











}

data class HomeUiState(
    val notesList: Resources<List<Notes>> = Resources.Loading(),
    val noteDeletedStatus: Boolean = false,
    val noNotes:Boolean = false,
)







