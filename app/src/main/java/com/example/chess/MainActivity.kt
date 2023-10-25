package com.example.chess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chess.ui.theme.ChessTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessTheme {
                ChessTable(rows = 10, cols = 10, size = 30)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Composable
fun ChessTable(rows: Int, cols:Int, size:Int){
    val size = remember {
        mutableStateOf(size.toFloat())
    }
    val alert = remember{ mutableStateOf(false ) }
    val alertX = remember { mutableStateOf(0) }
    val alertY = remember { mutableStateOf(0) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        size.value *= zoomChange
    }
    if (alert.value){
        AlertDialog(onDismissRequest = {alert.value = false}, text = { Text(text = "Вы нажали на (${alertX.value}, ${alertY.value}) ")},
            confirmButton = { TextButton(onClick = {alert.value = false}) {
            Text(text = "Ok")
        } })
    }
    Column(modifier = Modifier
        .transformable(state = state)
        .fillMaxSize()){
        for (i in 1..rows){
            Row {
                for (j in 1..cols) {
                    Box(modifier = Modifier
                        .width(size.value.dp)
                        .height(size.value.dp)
                        .background(if (j % 2 == i % 2) Color.White else Color.Black)
                        .clickable {
                            alertX.value = j;
                            alertY.value = i;
                            alert.value = true;
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChessTheme {
        Greeting("Android")
    }
}