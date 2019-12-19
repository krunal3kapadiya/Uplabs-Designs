package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.krunal3kapadiya.smartclock.R

import kotlinx.android.synthetic.main.fragment_stopwatch.*
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime


/**
 * @author krunal kapadiya
 * @link https://krunal3kapadiya.app
 * @date 14,April,2019
 */

class StopWatchFragment : Fragment() {
    companion object {
        private var MSG_UPDATE_TIME = 0
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
    private val mUpdateTimeHandler: Handler = UIUpdateHandler(this)


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


        add_alarm_button.setOnClickListener {
            runButtonClick(add_alarm_button)
            add_alarm_button.isActivated = !add_alarm_button.isActivated
        }

        text_reset.setOnClickListener {

        }
    }


    override fun onStart() {
        super.onStart()
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting and binding service")
        }
        val i = Intent(activity, StopWatchService::class.java)
        activity?.startService(i)
        activity?.bindService(i, mConnection, 0)
    }

    override fun onStop() {
        super.onStop()
        updateUIStopRun()
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

    fun runButtonClick(v: View?) {
        if (serviceBound && !stopWatchService!!.isTimerRunning) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Starting timer")
            }
            stopWatchService!!.startTimer()
            updateUIStartRun()
        } else if (serviceBound && stopWatchService!!.isTimerRunning) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Stopping timer")
            }
            stopWatchService!!.stopTimer()
            updateUIStopRun()
        }
    }

    /**
     * Updates the UI when a run starts
     */
    private fun updateUIStartRun() {
        mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME)
        add_alarm_button.isActivated = false
    }

    /**
     * Updates the UI when a run stops
     */
    private fun updateUIStopRun() {
        mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME)
        add_alarm_button.isActivated = true
        //        timerButton.setText(R.string.timer_start_button);
    }

    /**
     * Updates the timer readout in the UI; the service must be bound
     */
    private fun updateUITimer() {
        if (serviceBound) {
            alarm_clock.text = getFormattedNumbers(stopWatchService!!.elapsedTime())
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service bound")
            }
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
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service disconnect")
            }
            serviceBound = false
        }
    }

    internal class UIUpdateHandler(activity: StopWatchFragment) : Handler() {
        private val activity: WeakReference<StopWatchFragment> = WeakReference(activity)
        override fun handleMessage(message: Message) {
            if (MSG_UPDATE_TIME == message.what) {
                if (Log.isLoggable(TAG, Log.VERBOSE)) {
                    Log.v(TAG, "updating time")
                }
                activity.get()!!.updateUITimer()
                sendEmptyMessageDelayed(
                    MSG_UPDATE_TIME,
                    UPDATE_RATE_MS.toLong()
                )
            }
        }

        companion object {
            private const val UPDATE_RATE_MS = 1000
        }

    }


}