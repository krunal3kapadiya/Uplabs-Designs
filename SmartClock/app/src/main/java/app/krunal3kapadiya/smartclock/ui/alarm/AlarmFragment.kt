package app.krunal3kapadiya.smartclock.ui.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.krunal3kapadiya.smartclock.R
import app.krunal3kapadiya.smartclock.data.Alarm
import kotlinx.android.synthetic.main.fragment_alarm.*
import java.util.*
import kotlin.collections.ArrayList


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

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
//            PendingIntent.getBroadcast(context, 0, intent, 0)
//        }
//        alarmMgr?.set(
//            AlarmManager.ELAPSED_REALTIME_WAKEUP,
//            SystemClock.elapsedRealtime() + 60 * 1000,
//            alarmIntent
//        )

        val alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)


        val alarmList = ArrayList<Alarm>()
        val alarmListAdapter = AlarmListAdapter(alarmList)
        alarm_recycler_view.adapter = alarmListAdapter
        alarm_recycler_view.layoutManager = LinearLayoutManager(context)
        add_alarm_button.setOnClickListener {
            // Get Current Time
            val c: Calendar = Calendar.getInstance()
            val mHour = c.get(Calendar.HOUR_OF_DAY)
            val mMinute = c.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(
                context,
                OnTimeSetListener { _, hourOfDay, minute ->
                    val alarm = Alarm(
                        System.currentTimeMillis(),
                        "$hourOfDay:$minute",
                        false
                    )
                    alarmList.add(
                        alarm
                    )
//                    dbHelper.insertAlarm(alarm = alarm)
                    alarmListAdapter.notifyDataSetChanged()
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }
    }
}