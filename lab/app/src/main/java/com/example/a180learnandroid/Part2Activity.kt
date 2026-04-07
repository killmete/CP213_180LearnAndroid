package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme

// 1. สร้าง ViewModel จัดการ State และจำลองการดึงข้อมูลรายชื่อ
class ContactViewModel : ViewModel() {
    
    // ข้อมูล Mock จำลอง A-Z ทั้งหมด เพื่อให้ค่อยๆ ทยอยดึงทีละชุด
    private val allMockData: List<String> = ('A'..'Z').flatMap { letter ->
        (1..5).map { "$letter - Contact $it" }
    }
    
    // โหลดมาเป็นก้อนแรก 30 รายการก่อน
    private val pageSize = 30
    private var currentIndex = pageSize

    private val _contacts = MutableStateFlow<List<String>>(allMockData.take(currentIndex))
    val contacts: StateFlow<List<String>> = _contacts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 3. ฟังก์ชันจำลองการโหลดข้อมูลเพิ่ม
    fun loadMore() {
        if (_isLoading.value) return
        // ถ้าโหลดครบทุกตัวอักษรแล้ว ก็หยุดพัก
        if (currentIndex >= allMockData.size) return
        
        _isLoading.value = true
        viewModelScope.launch {
            // จำลองหน่วงเวลาการโหลด 2 วินาทีตามที่กำหนด
            delay(2000)
            
            val endIndex = (currentIndex + pageSize).coerceAtMost(allMockData.size)
            val newItems = allMockData.subList(currentIndex, endIndex)
            
            _contacts.value = _contacts.value + newItems
            currentIndex = endIndex
            
            _isLoading.value = false
        }
    }
}

class Part2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _180LearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactViewModel = viewModel()
) {
    val contacts by viewModel.contacts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // จับกลุ่ม List ของ String ตามตัวอักษรตัวแรก เพื่อไปทำ header
    val groupedContacts = contacts.groupBy { it.first().uppercaseChar() }
    val listState = rememberLazyListState()

    // ตรวจสอบว่า Scroll ถึงไอเท็มสุดทุ้ายหรือยัง
    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            if (totalItems == 0) return@derivedStateOf false

            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisible?.index == totalItems - 1
        }
    }

    // Trigger การโหลดข้อมูลเพิ่ม เมื่อถึงด้านล่าง List
    LaunchedEffect(isAtBottom) {
        if (isAtBottom && !isLoading) {
            viewModel.loadMore()
        }
    }

    // 2. ใช้ LazyColumn พร้อม stickyHeader แสดงรายชื่อ
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        groupedContacts.forEach { (initial, contactList) ->
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = initial.toString(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            items(contactList) { contact ->
                Text(
                    text = contact,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }

        // 4. แสดง CircularProgressIndicator ด้านล่างสุดเมื่อกำลังโหลดเพิ่ม
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}