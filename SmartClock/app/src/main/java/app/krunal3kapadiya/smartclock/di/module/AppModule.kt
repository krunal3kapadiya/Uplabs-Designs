package app.krunal3kapadiya.smartclock.di.module

import android.content.Context
import androidx.room.Room
import app.krunal3kapadiya.smartclock.data.AppDbHelper
import app.krunal3kapadiya.smartclock.data.SmartClockDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule{

    /*@Provides
    @Singleton
    fun provideDbHelper(): AppDbHelper {

        return AppDbHelper(
            Room.databaseBuilder(context, SmartClockDatabase::class.java, "data.db")
                .fallbackToDestructiveMigration()
                .build()
        )
    }*/
}