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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val intent = Intent(context, StopWatchService("service")::class.java)
        var i = 0
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
                        i = intent.getIntExtra(EXTENDED_DATA_STATUS, 0)
                        alarm_clock.text = "$i"
                    }
                }, IntentFilter(BROADCAST_ACTION)
            )
        }
    }
}