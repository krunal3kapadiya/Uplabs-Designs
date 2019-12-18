package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.krunal3kapadiya.smartclock.R
import kotlinx.android.synthetic.main.fragment_stopwatch.*
import kotlin.time.ExperimentalTime


/**
 * @author krunal kapadiya
 * @link https://krunal3kapadiya.app
 * @date 14,April,2019
 */

class StopWatchFragment : Fragment() {
    companion object {
        fun newInstance(): StopWatchFragment {
            return StopWatchFragment()
        }
    }

    private var pauseOffset: Long = 0L
    private var running = false

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

        alarm_clock.base = SystemClock.elapsedRealtime()

        add_alarm_button.setOnClickListener {
            if (add_alarm_button.isActivated) {
                if (running) {
                    alarm_clock.stop()
                    pauseOffset = SystemClock.elapsedRealtime() - alarm_clock.base
                    running = false
                }
            } else {
                if (!running) {
                    alarm_clock.setBase(SystemClock.elapsedRealtime() - pauseOffset)
                    alarm_clock.start()
                    running = true
                }

            }
            add_alarm_button.isActivated = !add_alarm_button.isActivated
        }

        text_reset.setOnClickListener {
            alarm_clock.base = SystemClock.elapsedRealtime()
            pauseOffset = 0
        }
    }
}