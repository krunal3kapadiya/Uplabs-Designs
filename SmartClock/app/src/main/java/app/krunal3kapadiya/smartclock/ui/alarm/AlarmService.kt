package app.krunal3kapadiya.smartclock.ui.alarm

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import app.krunal3kapadiya.smartclock.R
import app.krunal3kapadiya.smartclock.ui.MainActivity

class AlarmService : IntentService("AlarmService") {
    private var alarmNotificationManager: NotificationManager? = null
    public override fun onHandleIntent(intent: Intent?) {
        sendNotification("Wake Up! Wake Up!")
    }

    private fun sendNotification(msg: String) {
        Log.d("AlarmService", "Preparing to send notification...: $msg")
        alarmNotificationManager = this
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), 0
        )
        val alarmNotificationBuilder =
            NotificationCompat.Builder(this, AlarmService::class.java.name)
                .setContentTitle("Alarm")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
        alarmNotificationBuilder.setContentIntent(contentIntent)
        alarmNotificationManager!!.notify(1, alarmNotificationBuilder.build())
        Log.d("AlarmService", "Notification sent.")
    }
}
