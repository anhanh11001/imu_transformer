package ducletran.tech.imutransformer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class IMUApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant()
    }
}