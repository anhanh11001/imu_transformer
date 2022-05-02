package ducletran.tech.imutransformer.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import ducletran.tech.imutransformer.model.SensorData3D
import ducletran.tech.imutransformer.model.SensorInformation
import kotlinx.coroutines.flow.*
import timber.log.Timber

object HardwareSupport {

    fun oneShotVibrate(context: Context, vibrationTimeInMillis: Long = 700L) {
        val vibrator = ContextCompat.getSystemService(
            context,
            Vibrator::class.java
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(
                VibrationEffect.createOneShot(
                    vibrationTimeInMillis,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator?.vibrate(vibrationTimeInMillis)
        }
    }

    fun getSensorInformationFlow(context: Context): Flow<SensorInformation> {
        // Other type of sensors: Sensor.TYPE_GRAVITY, Sensor.TYPE_STEP_COUNTER ,
        // Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_LIGHT, Sensor.TYPE_PRESSURE
        // Sensor.TYPE_RELATIVE_HUMIDITY
        return combine(
            getSensorEventFlow(context, Sensor.TYPE_ACCELEROMETER),
            getSensorEventFlow(context, Sensor.TYPE_GYROSCOPE),
            getSensorEventFlow(context, Sensor.TYPE_MAGNETIC_FIELD),
        ) { acc, gyro, mag ->
            SensorInformation(
                accelerometerData = acc,
                gyroscopeData = gyro,
                magnetometerData = mag
            )
        }
    }

    private fun SensorEvent.toData3D() = SensorData3D(values[0], values[1], values[2])

    private fun getSensorEventFlow(
        context: Context,
        sensorType: Int
    ): Flow<SensorData3D> {
        val sensorManager = requireNotNull(
            ContextCompat.getSystemService(context, SensorManager::class.java)
        )
        val sensor = sensorManager.getDefaultSensor(sensorType)

        val sensorDataFlow = MutableStateFlow(SensorData3D())
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let { sensorDataFlow.value = it.toData3D() }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        return sensorDataFlow.onCompletion {
            sensorManager.unregisterListener(listener)
        }
    }
}