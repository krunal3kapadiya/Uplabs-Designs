package app.krunal3kapadiya.smartclock.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.krunal3kapadiya.smartclock.data.Alarm
import io.reactivex.Single

@Dao
interface AlarmDao {
    // add alarm
    @Insert
    fun insert(alarm: Alarm)

    // get all alarm
    @Query("SELECT * FROM Alarm")
    fun loadAll(): Single<List<Alarm?>?>?

    // delete alarm
    @Query("DELETE  FROM Alarm WHERE :id=id")
    fun delete(id: String): Single<List<Alarm?>?>?
}