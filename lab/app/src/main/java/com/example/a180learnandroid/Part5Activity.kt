package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// 1. สร้าง ViewModel เพื่อจัดการ Event 
class ErrorViewModel : ViewModel() {
    // ใช้ Channel สำหรับส่งข้อมูลแบบ "One-time event" (ทำหน้าที่เหมือน Event Bus)
    // สาเหตุที่ไม่ใช้ StateFlow เพราะ StateFlow จะจำค่าล่าสุดไว้ ซึ่งจะทำให้ Snackbar เด้งกลับมาอีกครั้งถ้ามีการ Rotate Screen
    private val _errorChannel = Channel<String>()
    
    // แปลงให้เป็น Flow สำหรับให้ UI (Composable) นำไป Observe
    val errorFlow = _errorChannel.receiveAsFlow()

    fun triggerError() {
        viewModelScope.launch {
            // จำลองการเกิด Error หลังจากเรียก API (ส่งข้อความเข้าไปในวง Channel)
            _errorChannel.send("เกิดข้อผิดพลาดในการดึงข้อมูลจาก Server!")
        }
    }
}

class Part5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _180LearnAndroidTheme {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, viewModel: ErrorViewModel = viewModel()) {
    // 2. สร้าง SnackbarHostState เพื่อช่วยควบคุมการโชว์/ซ่อน Snackbar ใน UI
    val snackbarHostState = remember { SnackbarHostState() }

    // 3. ใช้ LaunchedEffect (Side Effect) แบบ Unit เพื่อให้มันเริ่ม Observe ทันทีที่เข้าหน้านี้
    // การใช้ LaunchedEffect จะไม่เป็นการเอาตัวแปร State ไปผูกโยงกับ UI ตรงๆ แบบทั่วไป 
    // แต่จะเป็นการทำ "Side Effect" แทน ซึ่งเหมาะกับแสดงของที่โผล่ขึ้นมาแล้วจบไปแบบ Snackbar
    LaunchedEffect(Unit) {
        // Collect รอรับค่า Event แบบ Asynchronous
        viewModel.errorFlow.collect { errorMessage ->
            // เมื่อได้รับข้อมูล Error ใหม่ จะสั่งโชว์ Snackbar
            snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = "ปิด"
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        // นำ SnackbarHostState ไปผูกกับ Scaffold เพื่อกำหนดพื้นที่แสดงผลด้านล่างของจอ
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            // 4. ปุ่มสำหรับทดสอบการกด Trigger ดึง API ให้มี Snackbar ปรากฎขึ้น
            Button(onClick = { viewModel.triggerError() }) {
                Text("Trigger Error")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    _180LearnAndroidTheme {
        ErrorScreen()
    }
}