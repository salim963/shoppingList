package com.example.shoppinglist.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist.R
import com.example.shoppinglist.login.LoginUiState
import com.example.shoppinglist.model.Notes
import com.example.shoppinglist.repository.Resources
import com.example.shoppinglist.ui.theme.Utils
import com.example.shoppinglist.ui.theme.white
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    onNoteClick: (id: String) -> Unit,
    navToDetailPage: () -> Unit,
    navToLoginPage: () -> Unit,
) {
    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedNote: Notes? by remember {
        mutableStateOf(null)
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

   /* LaunchedEffect(key1 = Unit) {
        homeViewModel?.loadNotes()
    }*/
    LaunchedEffect(key1 = homeViewModel){
        homeViewModel?.loadNotes()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { navToDetailPage.invoke() }) {

                Text(" ADD Note ")

            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Shopping List")
                },

                actions = {
                    IconButton(onClick = {
                        navToDetailPage()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Note"
                        )
                    }
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },  drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Cyan)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Shopping List",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    navToDetailPage()
                }) {
                    Text(text = "Add Note")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if (homeViewModel != null) {
                        homeViewModel.signOut()
                    }
                    navToLoginPage()
                }) {
                    Text(text = "Logout")

                }
            }
        },
        /*floatingActionButton = {
            FloatingActionButton(onClick = { navToDetailPage.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        },*/


//        topBar = {
//
//            TopAppBar(
//
//                navigationIcon = {},
//                actions = {
//                    IconButton(onClick = {
//                        homeViewModel?.signOut()
//                        navToLoginPage.invoke()
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.ExitToApp,
//                            contentDescription = null,
//                        )
//                    }
//                },
//                title = {
//                    Text(text = "Home")
//                }
//            )
//        }
//        bottomBar = {
//            BottomAppBar(
//                cutoutShape = null,
//                elevation = 0.dp,
//                backgroundColor = Color.Magenta
//            ) {
//                IconButton(onClick = {
//                    navToLoginPage.invoke()
//                }) {
//
//                    Icon(
//                        imageVector = Icons.Default.ExitToApp,
//                        contentDescription = null,
//                    )
//                }
//            }
//        }
        bottomBar = { BottomBar()}



    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (homeUiState.notesList) {
                is Resources.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
                is Resources.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        contentPadding = PaddingValues(20.dp),

                    )
                    {
                        items(
                            homeUiState.notesList.data ?: emptyList()
                        ) { note ->
                            NoteItem(
                                notes = note,
                                onLongClick = {
                                    openDialog = true
                                    selectedNote = note
                                },
                            ) {
                                onNoteClick.invoke(note.documentId)
                            }

                        }


                    }
                    AnimatedVisibility(visible = openDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                openDialog = false
                            },
                            title = { Text(text = "Are u sure, Delete Note?") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        selectedNote?.documentId?.let {
                                            homeViewModel?.deleteNote(it)
                                        }
                                        openDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red
                                    ),
                                ) {
                                    Text(text = "Delete")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { openDialog = false }) {
                                    Text(text = "Cancel")
                                }
                            }
                        )


                    }
                }
                else -> {
                    Text(
                        text = homeUiState
                            .notesList.throwable?.localizedMessage ?: "Unknown Error",
                        color = Color.Red
                    )
                }


            }


        }

    }
    LaunchedEffect(key1 = homeViewModel?.hasUser) {
        if (homeViewModel?.hasUser == false) {
            navToLoginPage.invoke()
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    notes: Notes,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = { onLongClick.invoke() },
                onClick = { onClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Utils.colors[notes.colorIndex]
    ) {

        Column {
            Text(
                text = notes.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(4.dp),
                color = Color.White
            )
            Spacer(modifier = Modifier.fillMaxSize())
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.disabled
            ) {
                Text(
                    text = notes.description,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp),
                    maxLines = 4
                )

            }
            Spacer(modifier = Modifier.size(4.dp))
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.disabled
            ) {
                Text(
                    text = formatDate(notes.timestamp),
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.End),
                    maxLines = 4
                )

            }


        }


    }


}

@Composable
fun BottomBar() {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(elevation = 10.dp) {

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Home,"")
        },
            label = { Text(text = "Home") },
            selected = (selectedIndex.value == 0),
            onClick = {
                selectedIndex.value = 0
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Favorite,"")
        },
            label = { Text(text = "Favorite") },
            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Person,"")
        },
            label = { Text(text = "Profile") },
            selected = (selectedIndex.value == 2),
            onClick = {
                selectedIndex.value = 2
            })
    }
}
/*@Composable
fun ScaffoldWithBottomMenu() {
    Scaffold(bottomBar = {BottomBar()}
    ) {
        //content area
        Box(modifier = Modifier
            .background(Color(0xff546e7a))
            .fillMaxSize())
    }
}*/


private fun formatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("dd-MM-yy hh:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}


@Preview
@Composable
fun PrevHomeScreen() {

    Home(
        homeViewModel = null,
        onNoteClick = {},
        navToDetailPage = { /*TODO*/ },
    ) {

    }
}







@Composable
fun EmptyNotes(
    onClick : () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .clickable(
                    onClick = onClick
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(75.dp)
                    .alpha(0.5F),
                painter = painterResource(id = R.drawable.mynote),
                contentDescription = "Logo",
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Hmm...Looks like it's empty here!!",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = white.copy(0.4F),
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "ADD NOTES",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = white.copy(0.4F),
                    fontSize = 20.sp
                )
            )
        }

    }

}








