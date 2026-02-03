package com.example.a180learnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme
import com.example.a180learnandroid.ListActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            RPGCardView(
                onNextActivity = {
                    startActivity(Intent(this@MainActivity, ListActivity::class.java))
                }
            )
        }
    }
}

@Composable
fun RPGCardView(onNextActivity: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Gray)
        .padding(32.dp)) {

        // HP
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(color = Color.White)) {
            Text(
                text = "HP",
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .fillMaxWidth(fraction = 0.5f)
                    .background(color = Color.Red)
                    .padding(8.dp))
        }

        // Image
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(200.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(10.dp)
                .clickable {
                    onNextActivity.invoke()
                }
        )
        // Status
        var str by remember { mutableIntStateOf(5) }
        var agi by remember { mutableIntStateOf(5) }
        var int by remember { mutableIntStateOf(5) }
        var luk by remember { mutableIntStateOf(5) }
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(color = Color.White)
                .padding(0.dp, 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        )   {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    str++
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_keyboard_double_arrow_up_24),
                        contentDescription = "UP",
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(text = "STR", fontSize = 20.sp)
                Text(text = str.toString(), fontSize = 20.sp)

                Button(onClick = {
                    if(str > 0) str--
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_keyboard_double_arrow_up_24),
                        contentDescription = "DOWN",
                        modifier = Modifier.size(22.dp)
                            .rotate(180f)
                    )
                }
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    agi++
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_keyboard_double_arrow_up_24),
                        contentDescription = "UP",
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(text = "AGI", fontSize = 20.sp)
                Text(text = agi.toString(), fontSize = 20.sp)

                Button(onClick = {
                    if(agi > 0) agi--
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_keyboard_double_arrow_up_24),
                        contentDescription = "DOWN",
                        modifier = Modifier.size(22.dp)
                            .rotate(180f)
                    )
                }
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    int++
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_keyboard_double_arrow_up_24),
                        contentDescription = "UP",
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(text = "INT", fontSize = 20.sp)
                Text(text = int.toString(), fontSize = 20.sp)

                Button(onClick = {
                    if(int > 0) int--
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_keyboard_double_arrow_up_24),
                        contentDescription = "DOWN",
                        modifier = Modifier.size(22.dp)
                            .rotate(180f)
                    )
                }
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    luk++
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_keyboard_double_arrow_up_24),
                        contentDescription = "UP",
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(text = "LUK", fontSize = 20.sp)
                Text(text = luk.toString(), fontSize = 20.sp)

                Button(onClick = {
                    if(luk > 0) luk--
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_keyboard_double_arrow_up_24),
                        contentDescription = "DOWN",
                        modifier = Modifier.size(22.dp)
                            .rotate(180f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    RPGCardView({})
}