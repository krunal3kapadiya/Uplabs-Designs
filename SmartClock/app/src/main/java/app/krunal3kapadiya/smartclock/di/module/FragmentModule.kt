package app.krunal3kapadiya.smartclock.di.module

import app.krunal3kapadiya.smartclock.ui.alarm.AlarmFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindAlarmFragment(): AlarmFragment
}
