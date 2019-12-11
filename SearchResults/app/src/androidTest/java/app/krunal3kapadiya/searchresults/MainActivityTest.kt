package app.krunal3kapadiya.searchresults

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import app.krunal3kapadiya.searchresults.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkMainActivityViews() {
        onView(withId(R.id.filter_view)).check(matches(isDisplayed()))
        onView(withId(R.id.viewpager)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_filter_search)).check(matches(isDisplayed()))
        onView(withId(R.id.filter_search)).check(matches(isDisplayed()))
        onView(withId(R.id.tablayout)).check(matches(isDisplayed()))
        onView(withId(R.id.text_search)).check(matches(isDisplayed()))
    }

    @Test
    fun checkFilterButtonClick() {
        onView(withId(R.id.filter_search)).perform(click())
    }
}