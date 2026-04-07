package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme

// 1. สร้าง ViewModel จัดเก็บ State ด้วย mutableStateListOf 
class TodoViewModel : ViewModel() {
    // กำหนด Mock ข้อมูลเริ่มต้น 5 รายการ
    private val _todoItems = mutableStateListOf(
        "1. ไปจ่ายบิลค่าไฟ",
        "2. ซื้อของเข้าบ้าน",
        "3. อ่านหนังสือสอบ Android",
        "4. ล้างรถ",
        "5. พาแมวไปหาหมอ"
    )
    
    // โยนสถานะออกมาให้ View หรือ UI อ่านได้อย่างเดียว
    val todoItems: List<String> = _todoItems

    // ฟังก์ชันสั่งลบข้อมูลออกจาก State
    fun removeItem(item: String) {
        _todoItems.remove(item)
    }
}

class Part4Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _180LearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(modifier: Modifier = Modifier, viewModel: TodoViewModel = viewModel()) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        // แนะนำใช้ parameter key เพื่อให้ Compose ติดตาม Component ที่ถูกลบออกไปได้แม่นยำ
        items(viewModel.todoItems, key = { it }) { item ->
            
            // ควบคุม State การ Swipe 
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        // 4. สั่งให้ ViewModel ลบข้อมูล UI จะอัปเดตอัตโนมัติเมื่อ State เปลี่ยน
                        viewModel.removeItem(item)
                        true 
                    } else {
                        false 
                    }
                }
            )

            // 2. ใช้ SwipeToDismissBox ในการทำหน้าปัดซ้าย-ขวา
            SwipeToDismissBox(
                state = dismissState,
                // ปิดการปัดจากซ้ายไปขวาตาม Requirement
                enableDismissFromStartToEnd = false,
                backgroundContent = { // 3. ส่วนกล่องด้านหลังตอนถูกปัด
                    // เปลี่ยนสีพื้นหลังเป็นสีแดงเมื่อเป้าหมายเป็นการปัดลบ (EndToStart)
                    val backgroundColor by animateColorAsState(
                        targetValue = when (dismissState.targetValue) {
                            SwipeToDismissBoxValue.EndToStart -> Color(0xFFF44336) // Red
                            else -> Color.LightGray
                        },
                        label = "BackgroundColor"
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .background(backgroundColor),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        // Icon รูปถังขยะวางอยู่ขวาสุดเพราะเป็นการเลื่อนขวามาซ้าย (EndToStart)
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Icon",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                },
                content = { // ส่วน Card ด้านหน้า (ตัวข้อมูล)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    _180LearnAndroidTheme {
        TodoListScreen(viewModel = TodoViewModel())
    }
}