package com.example.a180learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a180learnandroid.ui.theme._180LearnAndroidTheme

class Part3Activity : ComponentActivity() {
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
                        // จำลองเรียกใช้งาน DonutChart พร้อมส่งค่าสีและตัวเลขตามกำหนด
                        DonutChart(
                            values = listOf(30f, 40f, 30f),
                            colors = listOf(
                                Color(0xFFFF6384), // แดงชมพู
                                Color(0xFF36A2EB), // ฟ้า
                                Color(0xFFFFCE56)  // เหลือง
                            ),
                            modifier = Modifier.size(220.dp) // กำหนดขนาดของกราฟโดยรวม
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DonutChart(
    values: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 80f // กำหนดความหนาของเส้นโค้งโดนัท
) {
    // คำนวณผลรวมทั้งหมดเพื่อใช้หาเปอร์เซ็นต์
    val total = values.sum()
    if (total == 0f) return

    // State สำหรับควบคุม Animation
    var animationPlayed by remember { mutableStateOf(false) }

    // Animate ค่าองศาที่วาด (Sweep Angle) จาก 0 ถึง 360
    val animateSweepAngle by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(
            durationMillis = 1500, // ใช้เวลาวาด 1.5 วินาที
            easing = FastOutSlowInEasing
        ),
        label = "DonutChartSweep"
    )

    // Trigger ให้เริ่มเล่น Animation ทันทีที่เปิดหน้าจอโผล่ขึ้นมา
    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    // เริ่มวาดด้วย Canvas
    Canvas(modifier = modifier) {
        var startAngle = -90f // เริ่มวาดจากด้านบนสุด (แกน Y ด้านบท หรือเทียบเท่า 12 นาฬิกา)
        var currentCalculatedTotalSweep = 0f

        for (i in values.indices) {
            // คำนวณองศาที่ต้องกวาดสำหรับชิ้นส่วนนี้ (จากสัดส่วนเทียบกับ 360 องศาเต็ม)
            val sweepAngle = (values[i] / total) * 360f

            // คำนวณองศาที่จะให้วาดจริง โดยเทียบชิ้นส่วนนี้กับรอบ Animation ปัจจุบัน (animateSweepAngle)
            val drawSweepAngle = minOf(
                sweepAngle,
                maxOf(0f, animateSweepAngle - currentCalculatedTotalSweep)
            )

            // ถ้าระยะองศาปัจจุบันอนุญาตให้วาดในชิ้นส่วนนี้ได้ ถึงจะสั่ง drawArc
            if (drawSweepAngle > 0) {
                // ป้องกันกรณีส่งสีมาไม่ครบตามจำนวนข้อมูล ให้ใช้สีเทาแทน
                val sliceColor = colors.getOrElse(i) { Color.Gray }

                drawArc(
                    color = sliceColor,
                    startAngle = startAngle,
                    sweepAngle = drawSweepAngle,
                    useCenter = false, // false = ไม่วาดเส้นเชื่อมจุดศูนย์กลาง (ทำให้ตรงกลางกลวงเป็นโดนัท)
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Butt // ตัดขอบตรงเพื่อให้ชิ้นส่วนต่อกันสนิท
                    )
                )
            }
            
            // ขยับจุดเริ่มต้นไปไว้หลังชิ้นส่วนปัจจุบัน สำหรับใช้วาดชิ้นส่วนถัดไป
            startAngle += sweepAngle
            currentCalculatedTotalSweep += sweepAngle
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DonutChartPreview() {
    _180LearnAndroidTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DonutChart(
                values = listOf(25f, 35f, 15f, 25f),
                colors = listOf(
                    Color.Red,
                    Color.Green,
                    Color.Blue,
                    Color.Magenta
                ),
                modifier = Modifier.size(250.dp)
            )
        }
    }
}