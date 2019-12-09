package app.krunal3kapadiya.smartclock.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import app.krunal3kapadiya.smartclock.R
import app.krunal3kapadiya.smartclock.ui.alarm.AlarmFragment
import app.krunal3kapadiya.smartclock.ui.clock.ClockFragment
import app.krunal3kapadiya.smartclock.ui.stopwatch.StopWatchFragment
import app.krunal3kapadiya.smartclock.ui.timer.TimerFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this,
                R.color.colorAccent
            )
        }
        setContentView(R.layout.activity_main)

        val adapter =
            TabViewPager(
                supportFragmentManager
            )
        // adding fragments with title
        adapter.addFragment(AlarmFragment.newInstance(), "Alarm")
        adapter.addFragment(ClockFragment.newInstance(), "Clock")
        adapter.addFragment(TimerFragment.newInstance(), "Timer")
        adapter.addFragment(StopWatchFragment.newInstance(), "StopWatch")
        // setting up adapter
        view_pager.adapter = adapter
        // adding viewpager to the tabview
        tab_view.setupWithViewPager(view_pager)

        val tabAlarm =
            LayoutInflater.from(this).inflate(R.layout.image_tab, null) as AppCompatImageView
        tabAlarm.setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.ic_alarm_gray_24dp
        ))

        val tabClock =
            LayoutInflater.from(this).inflate(R.layout.image_tab, null) as AppCompatImageView
        tabClock.setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.ic_alarm_gray_24dp
        ))

        val tabTimer =
            LayoutInflater.from(this).inflate(R.layout.image_tab, null) as AppCompatImageView
        tabTimer.setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.ic_timer_gray_24dp
        ))

        val tabStopWatch =
            LayoutInflater.from(this).inflate(R.layout.image_tab, null) as AppCompatImageView
        tabStopWatch.setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.ic_stop_watch_gray_24dp
        ))

        tab_view.getTabAt(0)?.customView = tabAlarm
        tab_view.getTabAt(1)?.customView = tabClock
        tab_view.getTabAt(2)?.customView = tabTimer
        tab_view.getTabAt(3)?.customView = tabStopWatch

        tab_view.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager))
    }

    /**
     * internal class for the view pager
     */
    internal class TabViewPager(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitleList = ArrayList<String>()

        /**
         * return fragment item
         */
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        /**
         * return total number of list
         */
        override fun getCount(): Int {
            return fragmentList.size
        }

        /**
         * adding fragment
         */
        fun addFragment(newInstance: Fragment, s: String) {
            fragmentList.add(newInstance)
            fragmentTitleList.add(s)
        }

        /**
         * setting fragment title
         */
        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }
    }
}
