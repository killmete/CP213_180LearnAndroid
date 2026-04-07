package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme

class Part1AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _180LearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        LikeButton()
                    }
                }
            }
        }
    }
}

@Composable
fun LikeButton(modifier: Modifier = Modifier) {
    // ใช้ State เก็บค่าสถานะของปุ่ม Like
    var isLiked by remember { mutableStateOf(false) }
    
    // ดักจับสถานะการกด (pressed) เพื่อใช้ทำ Scale animation
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // 1. Scale animation: ให้ปุ่มขยายใหญ่เมื่อกด และกลับไปขนาดเดิมเมื่อปล่อย ด้วย spring
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "Scale Animation"
    )

    // 2. Color animation: สีพื้นหลังปุ่มเปลี่ยนจากสีเทา เป็นสีชมพู (เมื่อ Liked)
    val backgroundColor by animateColorAsState(
        targetValue = if (isLiked) Color(0xFFE91E63) else Color.Gray,
        label = "Color Animation"
    )

    Button(
        onClick = { isLiked = !isLiked },
        modifier = modifier.scale(scale),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 3. AnimatedVisibility แสดง Icon หัวใจ
            AnimatedVisibility(visible = isLiked) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Liked Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            Text(text = if (isLiked) "Liked" else "Like")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LikeButtonPreview() {
    _180LearnAndroidTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LikeButton()
        }
    }
}