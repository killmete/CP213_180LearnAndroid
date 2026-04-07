package com.example.a180learnandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme

// Activity ที่ 1: MainActivity
class Part7Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // ขอฟีเจอร์จองพื้นที่เปิด Transition (จำเป็นเพื่อให้ใช้ Slide นอกเหนือจาก fade-in ธรรมดาได้)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _180LearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Activity ที่ 2: DetailActivity (เป้าหมายปลายทาง)
// **ข้อควรระวัง: ต้องนำไปประกาศ <activity android:name=".Part7DetailActivity" /> ใน AndroidManifest.xml ด้วย ถึงจะเปิดได้
class Part7DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 2. ขอฟีเจอร์รองรับ Transition 
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // สร้างและกำหนดค่า Transition แบบ Slide Up เข้ามาจากด้านล่างจอ
        // * สาเหตุที่ไม่ใช้ overrideActivityTransition() คือระบบ Android ปกติไม่มีไฟล์ Resource แนว Slide UP / Down รองรับโดยปริยาย
        // การใช้ android.transition.Slide จะได้ผลลัพธ์ที่ดีที่สุด ยืดหยุ่น และไม่ต้องสร้างไฟล์ XML แยกข้างนอกแม้แต่ไฟล์เดียวครับ
        val slideTransition = Slide(Gravity.BOTTOM).apply {
            duration = 350
        }
        window.enterTransition = slideTransition
        window.exitTransition = slideTransition

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. รับค่า Extra จาก Intent
        val message = intent.getStringExtra("EXTRA_MSG") ?: "No message received"

        setContent {
            _180LearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailScreen(
                        message = message,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // 3. Override ตัวคำสั่ง finish ให้แปลงไปเป็นคำสั่งจบพร้อม Animation สไลด์ลงแทน (Slide Down)
    override fun finish() {
        super.finishAfterTransition()
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current as Activity

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Main Activity", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // 1. ปุ่มเปิด DetailActivity ส่งค่า Intent ปกติ
        Button(onClick = {
            val intent = Intent(context, Part7DetailActivity::class.java).apply {
                putExtra("EXTRA_MSG", "Hello from Jetpack Compose Intent!")
            }

            // 2. ใช้ ActivityOptionsCompat สร้าง Scene เพื่อจุดประกาย Transition ให้หน้าต่างใหม่สไลด์ทับ
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context)
            context.startActivity(intent, options.toBundle())
        }) {
            Text("Launch Detail (Slide Up)")
        }
    }
}

@Composable
fun DetailScreen(message: String, modifier: Modifier = Modifier) {
    val activity = LocalContext.current as Activity

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Detail Activity", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Received Value: $message", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(32.dp))

        // 3. ปิดหน้าจอลงโดยคลิกเรียกคำสั่ง finish() ตามโจทย์ 
        // ซึ่งจะถูกดักจับและเปลี่ยนเป็น Slide Down ผ่านการ override ข้างบน
        Button(onClick = {
            activity.finish()
        }) {
            Text("Close (Slide Down)")
        }
    }
}