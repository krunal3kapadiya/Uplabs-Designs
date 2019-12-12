package app.krunal3kapadiya.smartclock

import android.app.Application
import com.facebook.stetho.Stetho

class SmartClockApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }


}