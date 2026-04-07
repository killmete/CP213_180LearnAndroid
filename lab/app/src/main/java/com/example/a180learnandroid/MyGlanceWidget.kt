package com.example.a180learnandroid

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.compose.ui.unit.dp

class MyGlanceWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(Color(0xFFE3F2FD)) // สีฟ้าอ่อน
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hello Glance!",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = ColorProvider(Color.Black)
                    )
                )
                Text(
                    text = "Widget สร้างด้วย Compose-like\nเสร็จสมบูรณ์!",
                    style = TextStyle(color = ColorProvider(Color.DarkGray))
                )
            }
        }
    }
}

class MyGlanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MyGlanceWidget()
}
