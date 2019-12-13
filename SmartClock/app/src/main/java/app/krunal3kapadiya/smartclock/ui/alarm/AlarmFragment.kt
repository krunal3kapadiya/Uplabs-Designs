package app.krunal3kapadiya.smartclock.ui.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
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

    val RQS_1 = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        SmartClockApplication.appComponent?.inject(this)

//        DaggerAppComponent.builder()
//            .application(activity?.application)
//            ?.build()?.inject(this)
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


                    val calNow = Calendar.getInstance()
                    val calSet = calNow.clone() as Calendar

                    calSet[Calendar.HOUR_OF_DAY] = hourOfDay
                    calSet[Calendar.MINUTE] = minute
                    calSet[Calendar.SECOND] = 0
                    calSet[Calendar.MILLISECOND] = 0

                    if (calSet.compareTo(calNow) <= 0) { //Today Set time passed, count to tomorrow
                        calSet.add(Calendar.DATE, 1)
                    }

                    //Adding alarm
                    val intent = Intent(context, MyAlarmReciever::class.java)
                    val pendingIntent =
                        PendingIntent.getBroadcast(context, RQS_1, intent, 0)
                    val alarmManager = context!!.getSystemService(ALARM_SERVICE) as AlarmManager
                    alarmManager[AlarmManager.RTC_WAKEUP, calSet.timeInMillis] =
                        pendingIntent

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