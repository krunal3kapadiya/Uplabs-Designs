package app.krunal3kapadiya.searchresults.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.krunal3kapadiya.searchresults.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPagerAdapter = MainFragmentStatePagerAdapter(supportFragmentManager)
        viewpager.adapter = viewPagerAdapter
        tablayout.setupWithViewPager(viewpager)
    }
}
