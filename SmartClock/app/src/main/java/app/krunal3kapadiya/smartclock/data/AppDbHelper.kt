package app.krunal3kapadiya.smartclock.data

import io.reactivex.Observable

class AppDbHelper(build: SmartClockDatabase) : DbHelper {

    var database: SmartClockDatabase = build

    override fun getAllAlarms(): Observable<List<Alarm?>?>? {
        return database.AlarmDao()?.loadAll()?.toObservable()
    }

    override fun insertAlarm(alarm: Alarm): Observable<Boolean?>? {
        return Observable.fromCallable {
            database.AlarmDao()?.insert(alarm)
            true
        }
    }
}