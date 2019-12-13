package app.krunal3kapadiya.smartclock.di

import android.app.Activity
import androidx.fragment.app.Fragment
import app.krunal3kapadiya.smartclock.SmartClockApplication
import app.krunal3kapadiya.smartclock.di.module.AppModule
import app.krunal3kapadiya.smartclock.di.module.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityBuilder::class, FragmentModule::class])
interface AppComponent {
    fun inject(app: SmartClockApplication)
    fun inject(activity: Activity)
    fun inject(fragment: Fragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(application: SmartClockApplication): Builder
    }
}
