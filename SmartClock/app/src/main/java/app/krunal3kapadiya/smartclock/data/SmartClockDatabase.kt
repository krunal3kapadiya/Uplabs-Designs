package app.krunal3kapadiya.smartclock.data

import androidx.room.Database
import androidx.room.RoomDatabase
import app.krunal3kapadiya.smartclock.data.dao.AlarmDao

@Database(entities = [Alarm::class], version = 1)
abstract class SmartClockDatabase : RoomDatabase() {
    abstract fun AlarmDao(): AlarmDao?
}
