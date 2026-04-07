package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class Part9Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CollapsingToolbarExample()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarExample() {
    // 1. สร้าง ScrollBehavior เพื่อจัดการการหดตัว/ขยายตัวของ TopAppBar
    // exitUntilCollapsedScrollBehavior จะหดจนเหลือขนาดเล็กสุดเมื่อเลื่อนเนื้อหาลง
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        // 3. เชื่อมต่อการ Scroll จากเนื้อหา (LazyColumn ด้านใน) เข้ากับ ScrollBehavior
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Part 9: Collapsing") },
                // 2. กำหนด ScrollBehavior ให้ TopAppBar ได้รับทราบการเลื่อน
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        ) {
            item {
                Text(
                    text = "Concept: Collapsing Toolbar",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "การทำ Collapsing Toolbar (แถบด้านบนที่หดตัวได้เมื่อเลื่อนหน้าจอ) ใน Jetpack Compose ประกอบด้วย 3 ส่วนสำคัญ:\n\n" +
                            "1. TopAppBarScrollBehavior: ทำหน้าที่จัดการพฤติกรรมการหด/ขยาย เช่น \n" +
                            "   - exitUntilCollapsedScrollBehavior: หดแถบให้เล็กลงจนเหลือขนาดเริ่มต้น\n" +
                            "   - enterAlwaysScrollBehavior: แถบจะโผล่กลับมาทันทีเมื่อเลื่อนขึ้นนิดเดียว\n\n" +
                            "2. TopAppBar: ใช้ LargeTopAppBar หรือ MediumTopAppBar แล้วตั้งค่าพารามิเตอร์ scrollBehavior ที่เราเตรียมไว้ให้มัน\n\n" +
                            "3. Modifier.nestedScroll(): ใช้กับ Scaffold หรือ Container หลักเพื่อเชื่อมการเลื่อนหน้าจอเข้ากับพฤติกรรมของ TopAppBar\n\n" +
                            "เมื่อเราเลื่อนหน้าจอ ข้อมูลการม้วนจอจะส่งผ่าน nestedScroll ไปที่ ScrollBehavior แถบด้านบนก็จะยุบหรือขยายตาม ลองเลื่อนรายการด้านล่างดู!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
            
            // เพิ่มไอเท็มจำลองเพื่อทดสอบการ Scroll
            items(50) { index ->
                Text(
                    text = "รายการที่ ${index + 1}",
                    modifier = Modifier.padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
