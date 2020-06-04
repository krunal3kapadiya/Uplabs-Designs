package app.krunal3kapadiya.smartclock.ui.alarm


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import app.krunal3kapadiya.smartclock.R
import app.krunal3kapadiya.smartclock.ui.MainActivity


class MyAlarmReciever : BroadcastReceiver() {
    var ct: Context? = null
    var title: String? = null
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        ct = context
        Toast.makeText(context, "OnReceive alarm test", Toast.LENGTH_SHORT).show()
        try {
            title = intent.extras!!["title"].toString()
            Toast.makeText(context, title, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Notification(context, "Please  pray for this prayer ")
    }

    fun Notification(
        context: Context,
        message: String?
    ) {

        val intent = Intent(context, MainActivity::class.java)
        // Send data to NotificationView Class
        intent.putExtra("title", title)
        intent.putExtra("text", title)
        // Open NotificationView.java Activity
        val pIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(
            context, "kk"
        ) // Set Icon
            .setSmallIcon(R.mipmap.ic_launcher) // Set Ticker Message
            .setTicker(message) // Set Title
            .setContentTitle(context.getString(R.string.app_name)) // Set Text
            .setContentText(message) // Add an Action Button below Notification
// Set PendingIntent into Notification
            .setContentIntent(pIntent) // Dismiss Notification
            .setAutoCancel(true)
        // Create Notification Manager
        val notificationmanager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Build Notification with Notification Manager
        notificationmanager.notify(38, builder.build())
    }
}