package com.example.a180learnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 48.dp, horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                Text("Previous Activities")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, SensorActivity::class.java))
                }) {
                    Text("SensorActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, GalleryPermissionActivity::class.java))
                }) {
                    Text("GalleryPermissionActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, RPGCardActivity::class.java))
                }) {
                    Text("RPGCardActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, PokedexActivity::class.java))
                }) {
                    Text("PokedexActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, LifeCycleComposeActivity::class.java))
                }) {
                    Text("LifeCycleComposeActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, CreateCopyActivity::class.java))
                }) {
                    Text("CreateCopyActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, SharedPreferencesActivity::class.java))
                }) {
                    Text("SharedPreferencesActivity")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                Text("New Parts")
                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part1AnimationActivity::class.java))
                }) {
                    Text("Part 1: Animation")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part2Activity::class.java))
                }) {
                    Text("Part 2: Contact List")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part3Activity::class.java))
                }) {
                    Text("Part 3: Donut Chart")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part4Activity::class.java))
                }) {
                    Text("Part 4: Swipe to Dismiss")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part5Activity::class.java))
                }) {
                    Text("Part 5: LaunchedEffect")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part6Activity::class.java))
                }) {
                    Text("Part 6: WebView")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part7Activity::class.java))
                }) {
                    Text("Part 7: Activity Transition")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part8Activity::class.java))
                }) {
                    Text("Part 8: Responsive Design")
                }
            }
        }
    }
}