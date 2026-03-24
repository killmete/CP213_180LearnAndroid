package com.example.a180learnandroid

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SensorData(val latitude: Double = 0.0, val longitude: Double = 0.0)

class SensorViewModel : ViewModel() {
    private val _sensorData = MutableStateFlow(SensorData())
    val sensorData: StateFlow<SensorData> = _sensorData.asStateFlow()

    fun updateSensorData(latitude: Double, longitude: Double) {
        _sensorData.value = SensorData(latitude, longitude)
    }
}
