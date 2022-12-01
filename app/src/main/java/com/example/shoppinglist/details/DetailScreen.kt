package com.example.shoppinglist.details

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.example.shoppinglist.ui.theme.Utils
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.shoppinglist.ui.theme.floatingB

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    noteId: String,
    onNavigate: () -> Unit,
) {
    val detailUiState = detailViewModel?.detailUiState ?: DetailUiState()

    val isFormsNotBlank = detailUiState.note.isNotBlank() &&
            detailUiState.title.isNotBlank()

//    val selectedColor by animateColorAsState(
//        targetValue = Utils.colors[detailUiState.colorIndex]
//    )
    val isNoteIdNotBlank = noteId.isNotBlank()
    val icon = if (isNoteIdNotBlank) Icons.Default.Refresh
    else Icons.Default.Check
    LaunchedEffect(key1 = Unit) {
        if (isNoteIdNotBlank) {
            detailViewModel?.getNote(noteId)
        } else {
            detailViewModel?.resetState()
        }
    }
    //val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            AnimatedVisibility(visible = isFormsNotBlank, enter= scaleIn(), exit = scaleOut() ) {
                FloatingActionButton(
                    onClick = {
                        if (isNoteIdNotBlank) {
                            detailViewModel?.updateNote(noteId)
                        } else {
                            detailViewModel?.addNote()
                        }
                    },
                    backgroundColor = floatingB,
                ) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                // .background(color = Color.LightGray)
                .padding(padding)
        ) {
            if (detailUiState.noteAddedStatus) {

                LaunchedEffect(scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Added Note Successfully")
                    detailViewModel?.resetNoteAdded()
                    onNavigate.invoke()
                }
            }

            if (detailUiState.noteUpdatedStatus) {
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Note Updated Successfully")
                    detailViewModel?.resetNoteAdded()
                    onNavigate.invoke()
                }
            }


            OutlinedTextField(
                value = detailUiState.title,
                onValueChange = {
                    detailViewModel?.onTitleChange(it)
                },
                label = { Text(text = "Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                   // .background(color = Color.Gray)
            )

            Text("Select your Note color please :", textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                contentPadding = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 8.dp,
                )
            ) {
                itemsIndexed(Utils.colors) { colorIndex, color ->
                    ColorItem(color = color) {
                        detailViewModel?.onColorChange(colorIndex)
                    }

                }
            }




            OutlinedTextField(
                value = detailUiState.note,
                onValueChange = { detailViewModel?.onNoteChange(it) },
                label = { Text(text = "Notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
                    //.background(color = Color.Gray)
            )


        }


    }


}

@Composable
fun ColorItem(

    color: Color,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    Surface(

        color = color,
        shape = CutCornerShape(17.dp),
        modifier = Modifier
            .padding(8.dp)
            .size(36.dp)

            .clickable {
                onClick.invoke()
                Toast
                    .makeText(context, "The Color is selected ", Toast.LENGTH_SHORT)
                    .show()

            },
        border = BorderStroke(2.dp, Color.Black)

    ) {

    }


}


@Preview
@Composable
fun PrevDetailScreen() {
    ShoppingListTheme {
        DetailScreen(detailViewModel = null, noteId = "") {

        }
    }

}

