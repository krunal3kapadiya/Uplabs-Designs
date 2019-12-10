package app.krunal3kapadiya.smartclock.ui.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import app.krunal3kapadiya.smartclock.R
import kotlinx.android.synthetic.main.fragment_alarm.*

/**
 * @author krunal kapadiya
 * @link https://krunal3kapadiya.app
 * @date 14,April,2019
 */

class AlarmFragment : Fragment() {
    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val stringList = ArrayList<String>()

        for (x in 0..10) {
            stringList.add("One")
        }

        val alarmListAdapter = AlarmListAdapter(stringList)
        alarm_recycler_view.adapter = alarmListAdapter
        alarm_recycler_view.layoutManager = LinearLayoutManager(context)
    }
}