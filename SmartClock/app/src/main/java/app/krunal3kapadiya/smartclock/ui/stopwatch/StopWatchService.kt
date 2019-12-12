package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.logging.Handler


class StopWatchService(name: String) : IntentService(name) {

    lateinit var mHandler: Handler
    var i: Int = 1
    var done = false

    override fun onHandleIntent(intent: Intent?) {
        done = false
        i = intent?.getIntExtra(StopWatchFragment.EXTENDED_DATA_STATUS, 0)!!

        while (!done) {
            Thread.sleep(1000)
            LocalBroadcastManager.getInstance(this).sendBroadcast(
                Intent(StopWatchFragment.BROADCAST_ACTION)
                    .putExtra(StopWatchFragment.EXTENDED_DATA_STATUS, i)
            )
            i++
        }
    }

    override fun onDestroy() {
        done = true
        super.onDestroy()
    }
}