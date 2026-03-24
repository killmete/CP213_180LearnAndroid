package com.example.a180learnandroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

class SensorActivity : ComponentActivity() {

    private val viewModel: SensorViewModel by viewModels()
    private lateinit var sensorTracker: SensorTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorTracker = SensorTracker(this)
        enableEdgeToEdge()
        setContent {
            val sensorData by viewModel.sensorData.collectAsState()
            var isTracking by remember { mutableStateOf(false) }

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || 
                              permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                if (granted) {
                    isTracking = true
                    sensorTracker.startListening { lat, lng ->
                        viewModel.updateSensorData(lat, lng)
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    sensorTracker.stopListening()
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "GPS Tracking Data", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Latitude: ${sensorData.latitude}", fontSize = 18.sp)
                Text(text = "Longitude: ${sensorData.longitude}", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(32.dp))

                if (isTracking) {
                    Button(onClick = {
                        isTracking = false
                        sensorTracker.stopListening()
                    }) {
                        Text("Stop Tracking")
                    }
                } else {
                    Button(onClick = {
                        val fineLocation = ContextCompat.checkSelfPermission(this@SensorActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                        val coarseLocation = ContextCompat.checkSelfPermission(this@SensorActivity, Manifest.permission.ACCESS_COARSE_LOCATION)

                        if (fineLocation == PackageManager.PERMISSION_GRANTED || coarseLocation == PackageManager.PERMISSION_GRANTED) {
                            isTracking = true
                            sensorTracker.startListening { lat, lng ->
                                viewModel.updateSensorData(lat, lng)
                            }
                        } else {
                            permissionLauncher.launch(
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                            )
                        }
                    }) {
                        Text("Start Tracking")
                    }
                }
            }
        }
    }
}
