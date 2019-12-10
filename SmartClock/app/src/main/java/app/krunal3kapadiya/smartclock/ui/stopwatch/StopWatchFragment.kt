package app.krunal3kapadiya.smartclock.ui.stopwatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.krunal3kapadiya.smartclock.R
import kotlinx.android.synthetic.main.fragment_stopwatch.*

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        floatingActionButton.setOnClickListener {
            // TODO start timer here
            floatingActionButton.isActivated = !floatingActionButton.isActivated
        }

    }
}