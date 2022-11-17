
package com.example.shoppinglist.screen


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppinglist.categories.MyFruitsList

@Composable
fun HomeScreen() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Magenta),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = "HOME",
//            fontSize = MaterialTheme.typography.h3.fontSize,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//    }
    MyFruitsList()
}

@Composable
@Preview
fun HomeScreenPreview() {
    MyFruitsList()
}




