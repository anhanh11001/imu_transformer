package ducletran.tech.imutransformer.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat

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
}