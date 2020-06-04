package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import app.krunal3kapadiya.smartclock.R
import app.krunal3kapadiya.smartclock.ui.MainActivity
import app.krunal3kapadiya.smartclock.ui.stopwatch.StopWatchFragment.Companion.getFormattedNumbers
import java.lang.ref.WeakReference

class StopWatchService : Service() {
    // Start and end times in milliseconds
    private var startTime: Long = 0
    private var endTime: Long = 0
    var isRunningInForeground = false
    lateinit var notificationManager: NotificationManagerCompat
    lateinit var notification: Notification

    /**
     * @return whether the timer is running
     */
    // Is the service tracking time?
    var isTimerRunning = false

    // Service binder
    private val serviceBinder: IBinder = RunServiceBinder()

    inner class RunServiceBinder : Binder() {
        val service: StopWatchService
            get() = this@StopWatchService
    }

    override fun onCreate() {

        Log.d(TAG, "Creating service")

        startTime = 0
        endTime = 0
        isTimerRunning = false
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent?.action == ARG_STOP_SERVICE) {
            stopForeground()
        }

        Log.d(TAG, "Starting service")

        return START_STICKY
    }

    private fun stopForeground() {
        if (isRunningInForeground) {
            notificationManager.cancel(NOTIFICATION_ID)
            isRunningInForeground = false
        }
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(intent: Intent): IBinder? {
        return serviceBinder
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
            Log.d(TAG, "$time")
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
        Log.d(TAG, "elapsedTime Time = $b")
        val time =
            if (endTime > startTime) endTime - startTime else System.currentTimeMillis() - startTime
        if (isRunningInForeground) {
            notification = getNotificationBuilder()!!.setContentText(getFormattedNumbers(time)).build()
            notificationManager.notify(
                NOTIFICATION_ID,
                notification
            )
        }
        return time
    }

    /**
     * Place the service into the foreground
     */
    fun foreground() {
        Log.d(TAG, "Starting service in foreground")
        isRunningInForeground = true
        notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    /**
     * Return the service to the background
     */
    fun background() {
        isRunningInForeground = false
        stopForeground(true)
    }

    /**
     * resetting clock
     */
    fun reset() {
        startTime = 0
        isTimerRunning = false
    }


    /**
     * Creates a notification for placing the service into the foreground
     *
     * @return a notification for interacting with the service when in the foreground
     */
    private fun createNotification(): Notification {

        return getNotificationBuilder()?.build()!!
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder? {
        val channelId: String = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                createNotificationChannel("my_service", "My Background Service")
            }
            else -> {
                ""
            }
        }
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )

        val stopIntent = Intent(this, StopWatchService::class.java)
        stopIntent.action = ARG_STOP_SERVICE
        val closeServicePendingIntent = PendingIntent.getService(
            this, 0,
            stopIntent, 0
        )
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_tab_stop_watch)
            .setOngoing(false)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_stop_white_24dp, "PAUSE", closeServicePendingIntent)
        return builder
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
        notificationManager =
            NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(chan)
        return channelId
    }

    companion object {
        private val TAG = StopWatchService::class.java.simpleName

        // Foreground notification id
        private const val NOTIFICATION_ID = 101
        private const val ARG_STOP_SERVICE = "STOP_SERVICE"
    }

    internal class UIUpdateHandler(activity: StopWatchFragment) : Handler() {
        private val activity: WeakReference<StopWatchFragment> = WeakReference(activity)
        override fun handleMessage(message: Message) {
            if (StopWatchFragment.MSG_UPDATE_TIME == message.what) {
                Log.d(TAG, "updating time")
                activity.get()!!.updateUITimer()
                sendEmptyMessageDelayed(
                    StopWatchFragment.MSG_UPDATE_TIME,
                    UPDATE_RATE_MS.toLong()
                )
            }
        }

        companion object {
            private const val UPDATE_RATE_MS = 1000
        }

    }
}