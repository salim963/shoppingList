package com.example.shoppinglist.categories

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R


@Composable
@Preview
fun MyFruitsList() {
    LazyColumn{
        items(myitems){
            ListItems(it)
        }
    }
}




@Composable
fun ListItems(myIcons: MyIcons) {
    val context = LocalContext.current
    Row (modifier = Modifier
            .fillMaxSize()
        .padding(10.dp),
        horizontalArrangement  =  Arrangement.SpaceBetween,

    )

    {
        Image(painter = painterResource(id = myIcons.icon),
            modifier = Modifier
                .size(102.dp)
                .padding(4.dp),
            contentDescription = "My ICons")

        Text(text = myIcons.name,
            modifier = Modifier.align(CenterVertically),
            color = Color.Blue,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold

        )
        Button(onClick = { Toast.makeText(context,"${myIcons.name} has been added to list", Toast.LENGTH_SHORT).show() },
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .align(CenterVertically),



            shape = RoundedCornerShape(30),

        )
         {
            Text(text = myIcons.button)
        }

    }

}
data class MyIcons(val icon: Int, val name:String, val button:String)
val myitems= listOf(
    MyIcons(R.drawable.ananas,"Ananas","Add to list"),
    MyIcons(R.drawable.apple,"Apple","Add to list"),
    MyIcons(R.drawable.banan,"Banana","Add to list"),
    MyIcons(R.drawable.kiwi,"Kiwi","Add to list"),
    MyIcons(R.drawable.orange,"Orange","Add to list"),
    MyIcons(R.drawable.papaya,"Papaya","Add to list"),
    MyIcons(R.drawable.stawberry,"Strawberry","Add to list"),
    MyIcons(R.drawable.watermelon,"Watermelon","Add to list")
)

