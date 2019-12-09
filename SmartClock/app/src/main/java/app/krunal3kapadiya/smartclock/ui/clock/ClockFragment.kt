package app.krunal3kapadiya.smartclock.ui.clock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.krunal3kapadiya.smartclock.R

/**
 * @author krunal kapadiya
 * @link https://krunal3kapadiya.app
 * @date 14,April,2019
 */

class ClockFragment : Fragment() {
    companion object {
        fun newInstance(): ClockFragment {
            return ClockFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_clock,
            container,
            false
        )
    }
}