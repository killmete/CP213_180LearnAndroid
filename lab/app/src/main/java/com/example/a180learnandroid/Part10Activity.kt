package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class Part10Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GlanceWidgetConcept()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlanceWidgetConcept() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Part 10: Glance Widget") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Concept: App Widget with Jetpack Glance",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "การทำ App Widget (หน้าต่างบน Home Screen) ในอดีตมักจะใช้ชุดคำสั่งที่เรียกว่า RemoteViews ทำงานร่วมกับไฟล์ layout XML ซึ่งมีข้อจำกัดและจัดการยาก\n\n" +
                        "แต่ปัจจุบัน เรามี Jetpack Glance ซึ่งเป็น API ที่ช่วยให้เราเขียน Widget ได้ด้วยภาษา Kotlin และใช้รูปแบบคล้ายคลึงกับ Jetpack Compose ทำให้ทำงานได้ง่ายขึ้นมาก\n\n" +
                        "องค์ประกอบหลักเมื่อใช้ Jetpack Glance:\n\n" +
                        "1. Dependency: ต้องลง androidx.glance:glance-appwidget ให้เรียบร้อย (ใน Build.Gradle)\n\n" +
                        "2. GlanceAppWidget: เป็นคลาสของฝั่ง Glance เอาไว้เขียน UI Widget คล้ายๆ Compose (เช่น Text, Column จะ import จาก androidx.glance ไม่ใช่ compose ui ทั่วไป)\n\n" +
                        "3. GlanceAppWidgetReceiver: ทำหน้าที่เป็นตัวรับสัญญาณจากระบบ เมื่อหน้าจอ Home Screen เรียกขอ Widget ของแอปเรา\n\n" +
                        "4. AppWidget Info (res/xml): ไฟล์สำหรับบอกระบบปฏิบัติการว่า Widget ของเรามีขนาดขั้นต่ำเท่าไหร่ หรือ อัปเดตทุกๆ กี่วินาที\n\n" +
                        "5. AndroidManifest.xml: เอาตัวบอกรับสัญญาณ (Receiver) ไปลงทะเบียนไว้พร้อมแนบ Widget Info XML\n\n" +
                        "ลองย่อแอป กลับไปหน้า Home ของมือถือค้างไว้ แล้วเพิ่ม Widget หาแอป 180LearnAndroid จะเจอ Widget ของหน้าพาร์ทนี้ให้ลากออกมาเล่นได้เลยครับ!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
