package com.example.a180learnandroid

import org.junit.Assert.assertEquals
import org.junit.Test

class SensorViewModelTest {

    @Test
    fun updateSensorData_updatesStateFlowCorrectly() {
        // Arrange
        val viewModel = SensorViewModel()
        val expectedLatitude = 13.736717
        val expectedLongitude = 100.523186

        // Act
        viewModel.updateSensorData(expectedLatitude, expectedLongitude)

        // Assert
        val currentData = viewModel.sensorData.value
        assertEquals(expectedLatitude, currentData.latitude, 0.0)
        assertEquals(expectedLongitude, currentData.longitude, 0.0)
    }

    @Test
    fun initialSensorData_isZeros() {
        // Arrange
        val viewModel = SensorViewModel()

        // Act
        val currentData = viewModel.sensorData.value

        // Assert
        assertEquals(0.0, currentData.latitude, 0.0)
        assertEquals(0.0, currentData.longitude, 0.0)
    }
}
