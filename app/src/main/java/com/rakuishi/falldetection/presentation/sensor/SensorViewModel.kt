package com.rakuishi.falldetection.presentation.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.math.abs
import kotlin.math.pow

class SensorViewModel @ViewModelInject constructor(
    @ApplicationContext context: Context
) : ViewModel(), SensorEventListener {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val isStoppedLiveData = MutableLiveData<Boolean>()
        .apply { postValue(true) }
    private var stoppedTimestamp: Long? = null

    fun start() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        /* do nothing */
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            handleAccelerometer(event)
        }
    }

    private fun handleAccelerometer(event: SensorEvent) {
        val g =
            abs(event.values[0]).pow(2) + abs(event.values[1]).pow(2) + abs(event.values[2]).pow(2)
        val r = g.pow(0.5f)
        val isStopped = r in 8.8..10.8

        if (isStopped) {
            if (stoppedTimestamp == null) {
                stoppedTimestamp = event.timestamp
            }

            val diff = event.timestamp - (stoppedTimestamp ?: event.timestamp)
            if (diff > 2 * 1000_000_000 && isStoppedLiveData.value == false) {
                isStoppedLiveData.postValue(true)
                stoppedTimestamp = null
            }
        } else {
            stoppedTimestamp = null
            if (isStoppedLiveData.value == true) {
                isStoppedLiveData.postValue(false)
            }
        }
    }
}