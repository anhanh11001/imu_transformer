package ducletran.tech.imutransformer.utils

import android.os.Environment
import ducletran.tech.imutransformer.model.Label
import ducletran.tech.imutransformer.model.SensorInformation
import java.util.*

object FileHelper {

    fun randomFileName(
        fileType: String = "csv"
    ): String = "data_${UUID.randomUUID()}.$fileType"

    fun formatSensorData(
        sensorData: SensorInformation,
        activityLabel: Label,
        phoneStateLabel: Label
    ): String {
        return arrayOf(
            sensorData.time.toString(),
            sensorData.accelerometerData.x.toString(),
            sensorData.accelerometerData.y.toString(),
            sensorData.accelerometerData.z.toString(),
            sensorData.magnetometerData.x.toString(),
            sensorData.magnetometerData.y.toString(),
            sensorData.magnetometerData.z.toString(),
            sensorData.gyroscopeData.x.toString(),
            sensorData.gyroscopeData.y.toString(),
            sensorData.gyroscopeData.z.toString(),
            phoneStateLabel.name,
            activityLabel.name,
        ).joinToString(",")
    }

    val columnNames = arrayOf(
        "date",
        "accelerometerX",
        "accelerometerY",
        "accelerometerZ",
        "magnetometerX",
        "magnetometerY",
        "magnetometerZ",
        "gyroscopeX",
        "gyroscopeY",
        "gyroscopeZ",
        "labelPhone",
        "labelActivity"
    ).joinToString(",")


    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
}