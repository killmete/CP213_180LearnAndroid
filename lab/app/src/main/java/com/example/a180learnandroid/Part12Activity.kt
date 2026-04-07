package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class Part12Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DialogAndBottomSheetExample()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAndBottomSheetExample() {
    // State สำหรับ Modal Bottom Sheet
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    // State สำหรับ AlertDialog
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Part 12: Dialog & Bottom Sheet") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Concept: Middle Dialog",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Middle Dialog (หรือ AlertDialog) เป็นคอมโพเนนต์ที่ป๊อปอัปขึ้นมาตรงกลางจอ โดยมักใช้ขัดจังหวะเพื่อแจ้งเตือน หรือ ยืนยันการกระทำที่สำคัญจากผู้ใช้\n\n" +
                        "การใช้งาน: จะทำงานตามตัวแปร State เมื่อตั้งเป็น true เราจึงเรียก AlertDialog วาดลงมา",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
                Text("แสดง Middle Dialog")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Concept: Modal Bottom Sheet",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Modal Bottom Sheet คือแผ่นที่แทรกขึ้นมาจากขอบประตูด้านล่างของจอ มักใช้ในงานตั้งค่า เมนูแชร์ หรือแสดงเนื้อหาตัวเลือกแบบไม่บดบังฉากหลัง\n\n" +
                        "การใช้งาน: เราใช้ ModalBottomSheet ควบคู่กับ ModalBottomSheetState เพื่อให้ Compose สามารถทำ Animation เปิด/ปิดได้อย่างนุ่มนวลอัตโนมัติ โดยเมื่อหน้าต่างถูกปัดลง State ทั่วไปที่เราเก็บไว้ก็จะต้องอัปเดตเป็น false ด้วย",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showBottomSheet = true }, modifier = Modifier.fillMaxWidth()) {
                Text("แสดง Modal Bottom Sheet")
            }

            // ============================================
            // การวาด Element Dialog & Bottom Sheet
            // คอมโพเนนต์เหล่านี้มักจะถูกวางไว้ด้านล่างสุดของ hierarchy
            // เพื่อใช้ z-index แสดงคลุมหน้าจออื่นๆ เมื่อตัวแปร state ของมันคือ true
            // ============================================

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { 
                        // event นี้เรียกเมื่อคลิกพื้นที่ด้านนอกกล่อง Dialog
                        showDialog = false 
                    },
                    icon = { Icon(Icons.Filled.Info, contentDescription = null) },
                    title = {
                        Text(text = "บันทึกข้อมูลหรือไม่?")
                    },
                    text = {
                        Text(text = "นี่คือ Middle Dialog ครับ หากเราต้องการให้ผู้ใช้เลือกตัดสินใจ เราสามารถใส่ปุ่มฝั่งซ้ายขวา (Confirm กับ Dismiss) เอาไว้ตรงด้านล่างนี้ได้ตามสะดวก")
                    },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("ตกลง (Confirm)")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("ยกเลิก (Dismiss)")
                        }
                    }
                )
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { 
                        // event นี้จะถูกเรียกเมื่อผู้ใช้กดยกเลิก หรือ ปัดหน้าจอลงไปจนสุด
                        showBottomSheet = false 
                    },
                    sheetState = sheetState
                ) {
                    // ส่วนเนื้อหาภายใน Bottom Sheet 
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("เนื้อหาเพิ่มเติมที่เลื่อนมาจากด้านล่าง", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "ใน Material 3 Bottom Sheet มี handle สีเทาเล็กๆ มาให้แล้วด้านบนเพื่อบอกว่ามันสไลด์ได้ และสามารถดึงลงเพื่อเก็บได้อย่างราบรื่น",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                // เราสามารถควบคุมการปิดได้ด้วยคำสั่ง hide() ผ่าน CoroutineScope 
                                // แล้วสลับค่า state ไปพร้อมๆกัน
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("ปิดแผ่นนี้จากปุ่มคำสั่งหลัก")
                        }
                        
                        // เว้นที่โล่งเล็กน้อยเพื่อให้ไม่ชิดขอบล่างเกินไป (กันการซ้อนทับ Navigation Bar ของเครื่องมือถือ)
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }
    }
}
