package ducletran.tech.imutransformer.model

import java.util.*

data class SensorInformation(
    val time: Date = Date(),
    val accelerometerData: SensorData3D = SensorData3D(),
    val magnetometerData: SensorData3D = SensorData3D(),
    val gyroscopeData: SensorData3D = SensorData3D(),
    val gravityData: SensorData3D = SensorData3D(),

    // Environment sensor
    val temperature: Float = 0F,
    val light: Float = 0F,
    val pressure: Float = 0F,
    val humidity: Float = 0F
)

data class SensorData3D(
    val x: Float = 0F,
    val y: Float = 0F,
    val z: Float = 0F,
)