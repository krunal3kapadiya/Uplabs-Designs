package app.krunal3kapadiya.smartclock

import android.app.Application
import app.krunal3kapadiya.smartclock.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SmartClockApplication : Application(), HasAndroidInjector {

    @set:Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }


    override fun androidInjector(): DispatchingAndroidInjector<Any> {
        return activityDispatchingAndroidInjector
    }

}