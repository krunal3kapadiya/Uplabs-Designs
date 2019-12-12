package app.krunal3kapadiya.smartclock.data

import io.reactivex.Observable

interface DbHelper {
    fun getAllAlarms(): Observable<List<Alarm?>?>?
    fun insertAlarm(alarm: Alarm): Observable<Boolean?>?
}
