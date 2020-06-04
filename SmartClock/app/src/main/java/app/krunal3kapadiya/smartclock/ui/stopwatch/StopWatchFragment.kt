package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.krunal3kapadiya.smartclock.R
import kotlinx.android.synthetic.main.fragment_stopwatch.*
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime


/**
 * @author krunal kapadiya
 * @link https://krunal3kapadiya.app
 * @date 14,April,2019
 */

class StopWatchFragment : Fragment() {
    companion object {
        var MSG_UPDATE_TIME = 0
        private val TAG = StopWatchFragment::class.java.simpleName

        fun newInstance(): StopWatchFragment {
            return StopWatchFragment()
        }

        fun getFormattedNumbers(number: Long): String {
            // long hours = (milliseconds / 1000) / 60 / 60;
            val hours = TimeUnit.MILLISECONDS.toHours(number)
            // long minutes = (milliseconds / 1000) / 60;
            val minutes = TimeUnit.MILLISECONDS.toMinutes(number)
            // long seconds = (milliseconds / 1000);
            var seconds = TimeUnit.MILLISECONDS.toSeconds(number)

            if (seconds > 60) {
                seconds %= 60
            }

            return "$hours: $minutes : $seconds"
        }
    }

    private var pauseOffset: Long = 0L
    private var running = false
    private var serviceBound = false
    private var stopWatchService: StopWatchService? = null
    private val mUpdateTimeHandler: Handler = StopWatchService.UIUpdateHandler(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    @ExperimentalTime
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        start_stop_stopwatch.setOnClickListener {
            runButtonClick(start_stop_stopwatch)
            start_stop_stopwatch.isActivated = !start_stop_stopwatch.isActivated
        }

        text_reset.setOnClickListener {
            stopWatchService?.reset()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Starting and binding service")
        val i = Intent(activity, StopWatchService::class.java)
        activity?.startService(i)
        activity?.bindService(i, mConnection, 0)
    }

    override fun onStop() {
        super.onStop()
//        updateUIStopRun()
        if (serviceBound) { // If a timer is active, foreground the service, otherwise kill the service
            if (stopWatchService!!.isTimerRunning) {
                stopWatchService!!.foreground()
            } else {
                activity?.stopService(Intent(activity, StopWatchService::class.java))
            }
            // Unbind the service
            activity?.unbindService(mConnection)
            serviceBound = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (stopWatchService != null) {
            if (stopWatchService?.isTimerRunning!!) {
                start_stop_stopwatch.isActivated = true
            }
        }
    }

    fun runButtonClick(v: View?) {
        if (serviceBound && !stopWatchService!!.isTimerRunning) {
            Log.d(TAG, "Starting timer")
            stopWatchService!!.startTimer()
            updateUIStartRun()
        } else if (serviceBound && stopWatchService!!.isTimerRunning) {
            Log.d(TAG, "Stopping timer")
            stopWatchService!!.stopTimer()
            updateUIStopRun()
        }
    }

    /**
     * Updates the UI when a run starts
     */
    private fun updateUIStartRun() {
        mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME)
        start_stop_stopwatch.isActivated = false
    }

    /**
     * Updates the UI when a run stops
     */
    private fun updateUIStopRun() {
        mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME)
        start_stop_stopwatch.isActivated = true
        //        timerButton.setText(R.string.timer_start_button);
    }

    /**
     * Updates the timer readout in the UI; the service must be bound
     */
    fun updateUITimer() {
        if (serviceBound) {
            alarm_clock.text = getFormattedNumbers(stopWatchService!!.elapsedTime())
        } else {
            stopWatchService!!.elapsedTime()
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            Log.d(TAG, "Service bound")

            val binder =
                service as StopWatchService.RunServiceBinder
            stopWatchService = binder.service
            serviceBound = true
            // Ensure the service is not in the foreground when bound
            stopWatchService?.background()
            // Update the UI if the service is already running the timer
            if (stopWatchService?.isTimerRunning!!) {
                updateUIStartRun()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {

            Log.d(TAG, "Service disconnect")

            serviceBound = false
        }
    }
}