package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class Part11Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SkeletonLoadingExample()
                }
            }
        }
    }
}

// 1. สร้าง Modifier พิเศษสำหรับวาดตัว Shimmer (แสงเงาขยับๆ บน Skeleton)
fun Modifier.shimmerEffect(): Modifier = composed {
    // กำหนด Animation วนลูปไม่สิ้นสุด
    val transition = rememberInfiniteTransition(label = "shimmer_transition")
    val translateAnim by transition.animateFloat(
        initialValue = -300f, // เริ่มต้นก่อนกรอบจะแสงเข้า
        targetValue = 1500f,  // สิ้นสุดเมื่อพ้นมุมกรอบออกไป
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_anim"
    )

    // สไตล์สีของ Skeleton จะใช้เทาสลับกันไปมาเพื่อให้ดูเหมือนมีแสงผ่าน
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f), // สีตรงกลางจางกว่า
        Color.LightGray.copy(alpha = 0.6f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnim - 300f, y = translateAnim - 300f),
        end = Offset(x = translateAnim, y = translateAnim)
    )

    // คืนค่าเป็นตัวมันเองที่เติมพื้นหลังเป็น Shimmer แล้ว
    this.background(brush)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkeletonLoadingExample() {
    // จำลองสถานะของการโหลดข้อมูล
    var isLoading by remember { mutableStateOf(true) }

    // ใช้ LaunchedEffect ในการตั้งเวลา 3 วินาที หลอกเหมือนดึงข้อมูลเสร็จแล้ว
    LaunchedEffect(Unit) {
        delay(3000)
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Part 11: Skeleton Loading") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Concept: Skeleton Loading",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Skeleton Loading เป็นเทคนิคช่วยลดความรำคาญของผู้ใช้ระหว่างรอโหลดข้อมูลครับ โดยหลักการจะมีดังนี้:\n\n" +
                        "1. วาดก้อน Placeholder: โดยเราจะวาดและจำลอง Layout เป็นโครงสร้างใกล้เคียงของจริงที่สุด แต่แทนที่จะใส่ข้อมูลจริง เราจะใส่ Block สีเทา (Box) แทนรูปหรือข้อความ\n\n" +
                        "2. ใส่ Shimmer Animation: โครงสร้างสีเทาตั้งไว้เฉยๆอาจดูตายตัว เราจึงเขียน Modifier ลัดที่บรรจุ InfiniteTransition ใช้สีแบบ Gradient (linearGradient) ขยับตำแหน่งซ้ายไปขวา ทำให้ดูเหมือนกำลังโหลดตลอดเวลาอย่างนุ่มนวล\n\n" +
                        "3. สลับ State: เมื่อโหลดผลเสร็จ (isLoading = false) เราก็จะซ่อนโครงสร้างนี้ และนำ Component แสดงข้อมูลแผ่นจริงมาวาดลงไปแทนที่\n\n" +
                        "ข้อสังเกต: ลองนับ 1..2..3 โครงสีเทาด้านล่างจะหายไปแล้วแทนด้วยเป้าหมายจริงครับ",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            // เงื่อนไขในการ Render ว่าจะโชว์ Skeleton หรือข้อมูลจริง
            if (isLoading) {
                SkeletonItem()
                Spacer(modifier = Modifier.height(16.dp))
                SkeletonItem()
            } else {
                RealContentItem(title = "Hello Compose!", subtitle = "Data loaded successfully from server.")
                Spacer(modifier = Modifier.height(16.dp))
                RealContentItem(title = "Skeleton UI Test", subtitle = "Animations makes apps feel much faster.")
            }
        }
    }
}

@Composable
fun SkeletonItem() {
    Row(modifier = Modifier.fillMaxWidth()) {
        // วงกลมทางซ้ายจำลองรูป Profile
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .shimmerEffect() // เรียก Modifier ที่เราสร้างไว้ข้างบน!
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            // แท่งบรรทัดแรกจำลองเส้นหัวข้อหลัก
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
            // แท่งบรรทัดทีั่สองจำลอง Subtitle
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
        }
    }
}

@Composable
fun RealContentItem(title: String, subtitle: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFF4CAF50))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
