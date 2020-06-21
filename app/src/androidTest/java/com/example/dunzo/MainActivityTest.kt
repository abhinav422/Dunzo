package com.example.dunzo

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.dunzo.view.MainActivity
import org.hamcrest.CoreMatchers.allOf

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)


    @Test
    fun test_search__edit_text_visibility_gone() {
        SystemClock.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        SystemClock.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("Catalysts"))
        SystemClock.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.ok)).perform(ViewActions.click())
        SystemClock.sleep(1000)
        Espresso.onView(withId(R.id.search_edit_text))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed()))
            )
    }

    @Test
    fun test_search_functinality() {
        SystemClock.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        SystemClock.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("Catalysts"))
        SystemClock.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.ok)).perform(ViewActions.click())
        SystemClock.sleep(5000)
        Espresso.onView(first(withId(R.id.title)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }



    private fun <T> first(matcher: Matcher<T>): Matcher<T> {
        return object : BaseMatcher<T>() {
            internal var isFirst = true

            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }

                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("should return first matching item")
            }
        }
    }
}
