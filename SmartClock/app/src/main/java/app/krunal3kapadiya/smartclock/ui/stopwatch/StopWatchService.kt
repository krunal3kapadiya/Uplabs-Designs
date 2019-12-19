package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import app.krunal3kapadiya.smartclock.R
import app.krunal3kapadiya.smartclock.ui.MainActivity

class StopWatchService : Service() {
    // Start and end times in milliseconds
    private var startTime: Long = 0
    private var endTime: Long = 0
    /**
     * @return whether the timer is running
     */
    // Is the service tracking time?
    var isTimerRunning = false
        private set
    // Service binder
    private val serviceBinder: IBinder = RunServiceBinder()

    inner class RunServiceBinder : Binder() {
        val service: StopWatchService
            get() = this@StopWatchService
    }

    override fun onCreate() {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Creating service")
        }
        startTime = 0
        endTime = 0
        isTimerRunning = false
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting service")
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Binding service")
        }
        return serviceBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Destroying service")
        }
    }

    /**
     * Starts the timer
     */
    fun startTimer() {
        if (!isTimerRunning) {
            startTime = if (endTime == 0L) {
                System.currentTimeMillis()
            } else {
                endTime
            }
            val time = startTime / 1000
            Log.d("TimerS", "$time")

            isTimerRunning = true
        } else {
            Log.e(
                TAG,
                "startTimer request for an already running timer"
            )
        }
    }

    /**
     * Stops the timer
     */
    fun stopTimer() {
        if (isTimerRunning) {
            endTime = System.currentTimeMillis()
            isTimerRunning = false
        } else {
            Log.e(
                TAG,
                "stopTimer request for a timer that isn't running"
            )
        }
    }

    /**
     * Returns the  elapsed time
     *
     * @return the elapsed time in seconds
     */
    fun elapsedTime(): Long { // If the timer is running, the end time will be zero
        val b = endTime > startTime
        Log.d("TimerS", "$b")
        return if (endTime > startTime) endTime - startTime else System.currentTimeMillis() - startTime
    }

    /**
     * Place the service into the foreground
     */
    fun foreground() {
        startForeground(NOTIFICATION_ID, createNotification())
    }

    /**
     * Return the service to the background
     */
    fun background() {
        stopForeground(true)
    }

    /**
     * Creates a notification for placing the service into the foreground
     *
     * @return a notification for interacting with the service when in the foreground
     */
    private fun createNotification(): Notification {
        val channelId: String
        channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("my_service", "My Background Service")
        } else { // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
            ""
        }
        val remoteViews = RemoteViews(packageName, R.layout.row_notification)
        remoteViews.setImageViewResource(R.id.app_icon, R.mipmap.ic_launcher)
        remoteViews.setTextViewText(R.id.notification_title, getString(R.string.app_name))
        remoteViews.setTextViewText(
            R.id.notification_sub_title,
            "Notification Custom Title"
        )
//        val builder = NotificationCompat.Builder(this, channelId)
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//            .setCustomContentView(remoteViews)
//            .setSmallIcon(R.mipmap.ic_launcher)

        val builder = NotificationCompat.Builder(this, channelId)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//            .setCustomContentView(remoteViews)
            .setSmallIcon(R.mipmap.ic_launcher)
        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
            this, 0, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(resultPendingIntent)
        return builder.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        channelId: String,
        channelName: String
    ): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    companion object {
        private val TAG = StopWatchService::class.java.simpleName
        // Foreground notification id
        private const val NOTIFICATION_ID = 1
    }
}