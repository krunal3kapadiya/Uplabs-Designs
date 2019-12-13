package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.logging.Handler


class StopWatchService : IntentService("name") {

    lateinit var mHandler: Handler
    var i: Long = 1
    var done = false

    override fun onCreate() {
        super.onCreate()
        Log.d(StopWatchService::class.java.name, "Service Created")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(StopWatchService::class.java.name, "Handling Intent")
        done = false
        i = intent?.getLongExtra(StopWatchFragment.EXTENDED_DATA_STATUS, 0)!!

        while (!done) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(
                Intent(StopWatchFragment.BROADCAST_ACTION)
                    .putExtra(StopWatchFragment.EXTENDED_DATA_STATUS, i)
            )
            i++
        }
    }

    override fun onDestroy() {
        done = true
        Log.d(StopWatchService::class.java.name, "Service Stopped")
        super.onDestroy()
    }
}