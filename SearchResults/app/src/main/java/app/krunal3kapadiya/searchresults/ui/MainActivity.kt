package app.krunal3kapadiya.searchresults.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.krunal3kapadiya.searchresults.R
import app.krunal3kapadiya.searchresults.ui.filterDialog.FilterDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPagerAdapter = MainFragmentStatePagerAdapter(supportFragmentManager)
        viewpager.adapter = viewPagerAdapter
        tablayout.setupWithViewPager(viewpager)

        iv_filter_search.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val prev = supportFragmentManager.findFragmentByTag("dialog")
            if (prev != null) {
                fragmentTransaction.remove(prev)
            }
            fragmentTransaction.addToBackStack(null)
            val dialogFragment = FilterDialogFragment()
            dialogFragment.show(fragmentTransaction, "dialog")
        }
    }
}
