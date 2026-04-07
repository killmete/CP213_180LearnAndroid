package com.example.a180learnandroid

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 1. สร้าง ViewModel เพื่อเก็บ State URL เบราว์เซอร์
class WebBrowserViewModel : ViewModel() {
    private val _url = MutableStateFlow("https://www.google.com")
    val url: StateFlow<String> = _url.asStateFlow()

    fun updateUrl(newUrl: String) {
        // แอบเช็กความถูกต้องเบื้องต้น เพื่อให้หน้าเว็บพร้อมใช้งานเสมอ (เพิ่ม https:// ถ้าไม่ได้พิมพ์มา)
        var validUrl = newUrl.trim()
        if (!validUrl.startsWith("http://") && !validUrl.startsWith("https://")) {
            validUrl = "https://$validUrl"
        }
        _url.value = validUrl
    }
}

class Part6Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _180LearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebBrowserScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WebBrowserScreen(modifier: Modifier = Modifier, viewModel: WebBrowserViewModel = viewModel()) {
    // ดึงสถานะ URL ล่าสุดจาก ViewModel
    val currentUrl by viewModel.url.collectAsState()
    
    // สถานะสำหรับ TextField โดยเฉพาะ แยกจาก ViewModel เพื่อจะได้พิมพ์ได้ลื่นไหลก่อนกด Go
    var inputUrl by remember { mutableStateOf(currentUrl) }

    Column(modifier = modifier.fillMaxSize()) {
        // 4. สร้าง TextField สำหรับพิมพ์และปุ่มกดเพื่อส่งข้อมูลให้ ViewModel ทำงาน
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputUrl,
                onValueChange = { inputUrl = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                placeholder = { Text("Enter Website URL") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.updateUrl(inputUrl) }) {
                Text("Go")
            }
        }

        // 2 & 5. ใช้ AndroidView ผูกกับหน้า WebView ทำงานร่วมกับ ViewModel อย่างสมบูรณ์
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            // factory ใช้สำหรับสร้างชิ้นส่วน View ดั้งเดิม ครั้งแรกและครั้งเดียว!
            factory = { context ->
                WebView(context).apply {
                    // เปิดให้ WebView อนุญาตเล่น JavaScript ได้ 
                    settings.javaScriptEnabled = true
                    
                    // 3. กำหนดค่า WebViewClient ไม่ให้เมื่อผู้ใช้กดลิงก์แล้วเด้งแอปโผล่ออกไปที่ Chrome หรือ External Browser ของมือถือ
                    webViewClient = WebViewClient()
                }
            },
            // update จะถูกเรียกทุกครั้งที่มี State ในฝั่ง Compose เปลี่ยนแปลง ในกรณีนี้คือ currentUrl 
            update = { webView ->
                // ไม่ได้ทำการสร้าง WebView ใหม่ แต่เพียงสั่งให้มันโหลดหน้า URL ต่อแค่นั้น
                webView.loadUrl(currentUrl)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WebBrowserScreenPreview() {
    _180LearnAndroidTheme {
        WebBrowserScreen()
    }
}