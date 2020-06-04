package app.krunal3kapadiya.smartclock.ui.alarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.krunal3kapadiya.smartclock.R
import app.krunal3kapadiya.smartclock.data.Alarm
import kotlinx.android.synthetic.main.row_alarm_list.view.*

class AlarmListAdapter(private val alarmList: List<Alarm>) :
    RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(alarm: Alarm) {
            itemView.text_alarm_time.text = alarm.alarmTime
            itemView.alarm_activated_switch.isActivated= alarm.isActivated
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_alarm_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

}