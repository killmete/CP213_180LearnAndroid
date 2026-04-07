package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme

class Part8Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _180LearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ResponsiveProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ResponsiveProfileScreen(modifier: Modifier = Modifier) {
    // 1. ใช้ BoxWithConstraints ฝังตัวเป็นรากของ Layout 
    // เพื่อให้มันสามารถดึงค่า maxWidth มาตรวจสอบได้อัตโนมัติทุกครั้งที่ขนาดหน้าจอเปลี่ยน
    BoxWithConstraints(modifier = modifier.fillMaxSize().padding(24.dp)) {
        
        // 2. ถ้าความกว้างหน้าจอน้อยกว่า 600.dp (หน้าจอแคบ เช่น มือถือแนวตั้ง)
        if (maxWidth < 600.dp) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePicture(modifier = Modifier.size(150.dp))
                Spacer(modifier = Modifier.height(24.dp))
                ProfileDetails(modifier = Modifier.fillMaxWidth(), centerAlignment = true)
            }
        } 
        // 3. ถ้าความกว้างหน้าจอตั้งแต่ 600.dp ขึ้นไป (หน้าจอกว้าง เช่น มือถือแนวนอน หรือ แท็บเล็ต)
        else {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // จัดสัดส่วนการแสดงผลแบ่งเป็น 1 ส่วน (รูปภาพ) : 2 ส่วน (ข้อความรายละเอียด)
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    ProfilePicture(
                        modifier = Modifier
                            .fillMaxWidth(0.8f) // ใช้ความกว้าง 80% ของพื้นที่ 1 ส่วน
                            .aspectRatio(1f) // บังคับให้เป็นสี่เหลี่ยมจตุรัสเสมอ
                    )
                }
                
                Spacer(modifier = Modifier.width(32.dp))
                
                Box(modifier = Modifier.weight(2f)) {
                    ProfileDetails(modifier = Modifier.fillMaxWidth(), centerAlignment = false)
                }
            }
        }
    }
}

// Composable สำหรับแสดงรูป Profile (กล่องสีเทากลมๆ)
@Composable
fun ProfilePicture(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile Picture",
            modifier = Modifier.fillMaxSize(0.6f),
            tint = Color.DarkGray
        )
    }
}

// Composable สำหรับแสดงรายละเอียดข้อมูลส่วนตัว
@Composable
fun ProfileDetails(modifier: Modifier = Modifier, centerAlignment: Boolean) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (centerAlignment) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Text("ข้อมูลส่วนตัว", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("ชื่อ: สมชาย ใจดี", style = MaterialTheme.typography.bodyLarge)
        Text("อายุ: 25 ปี", style = MaterialTheme.typography.bodyLarge)
        Text("ตำแหน่ง: Mobile Developer", style = MaterialTheme.typography.bodyLarge)
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "ความสนใจ: Android, Jetpack Compose, Kotlin, Responsive UX/UI",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

// Preview ทดสอบหน้าจอแบบแนวตั้ง (มือถือปกติ)
@Preview(showBackground = true, name = "Portrait (Phone)", widthDp = 400)
@Composable
fun ResponsiveProfilePhonePreview() {
    _180LearnAndroidTheme {
        ResponsiveProfileScreen()
    }
}

// Preview ทดสอบหน้าจอแบบแนวนอน (Tablet/Landscape)
@Preview(showBackground = true, name = "Landscape (Tablet)", widthDp = 800)
@Composable
fun ResponsiveProfileTabletPreview() {
    _180LearnAndroidTheme {
        ResponsiveProfileScreen()
    }
}