package app.krunal3kapadiya.smartclock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class Alarm(
    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    var createdAt: Long,
    @SerializedName("alarmTime")
    @ColumnInfo(name = "alarmTime")
    var alarmTime: String? = null,
    @Expose
    @SerializedName("isActivated")
    @ColumnInfo(name = "isActivated")
    var isActivated: Boolean = false
)