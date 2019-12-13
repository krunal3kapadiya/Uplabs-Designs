package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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
        val BROADCAST_ACTION: String? = "app.krunal3kapadiya.smartclock.ui.BROADCAST"
        val EXTENDED_DATA_STATUS: String? = "app.krunal3kapadiya.smartclock.ui.BROADCAST"

        fun newInstance(): StopWatchFragment {
            return StopWatchFragment()
        }
    }

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

        val intent = Intent(context, StopWatchService::class.java)
        var i = 0L
        add_alarm_button.setOnClickListener {
            // TODO start timer here
            if (add_alarm_button.isActivated) {
                activity?.stopService(intent)
            } else {
                activity?.startService(intent.putExtra(EXTENDED_DATA_STATUS, i))
            }
            add_alarm_button.isActivated = !add_alarm_button.isActivated
        }

        activity?.applicationContext?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        i = intent.getLongExtra(EXTENDED_DATA_STATUS, 0)
                        alarm_clock.text = getFormattedNumbers(i)
                    }
                }, IntentFilter(BROADCAST_ACTION)
            )
        }

        text_reset.setOnClickListener {
            i = 0
            alarm_clock.text = getString(R.string._00_00_00)
        }
    }

    fun getFormattedNumbers(number: Long): String {
        // long hours = (milliseconds / 1000) / 60 / 60;
        val hours = TimeUnit.MILLISECONDS.toHours(number)
        // long minutes = (milliseconds / 1000) / 60;
        val minutes = TimeUnit.MILLISECONDS.toMinutes(number)
        // long seconds = (milliseconds / 1000);
        val seconds = TimeUnit.MILLISECONDS.toSeconds(number)
        return "$hours: $minutes : $seconds"
    }
}