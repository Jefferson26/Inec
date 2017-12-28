package com.inec.android.inec;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


//Teste para a UI
@RunWith(AndroidJUnit4.class)
public class ResetButtonTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void resetButtonTest() {
        onView(withId(R.id.action_refresh)).perform(click());
        onView(withId(R.id.layout)).check(matches(isDisplayed()));
    }

    @Test
    public void resetButtonWithUserTyped() {
        onView(withId(R.id.input_user)).perform(typeText("jefferson26"), closeSoftKeyboard());
        onView(withId(R.id.action_refresh)).perform(click());
        onView(withId(R.id.layout)).check(matches(isDisplayed()));
    }
}
