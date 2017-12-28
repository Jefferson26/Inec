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

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.not;

//Teste para a UI
@RunWith(AndroidJUnit4.class)
public class MainActivityTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void searchCorrectUserWithNameTest() {
        onView(withId(R.id.input_user)).perform(typeText("torvalds"), closeSoftKeyboard());
        onView(withId(R.id.btn_search)).perform(click());
        onView(withId(R.id.tv_account)).check(matches(withText("torvalds")));
        onView(withId(R.id.tv_name)).check(matches(withText("Linus Torvalds")));
    }

    @Test
    public void searchCorrectUserWithNameEmptyTest() {
        onView(withId(R.id.input_user)).perform(typeText("jefferson26"), closeSoftKeyboard());
        onView(withId(R.id.btn_search)).perform(click());
        onView(withText("Name not Registered")).check(matches(isDisplayed()));
    }

    @Test
    public void searchUserNotRegisteredTest() {
        onView(withId(R.id.input_user)).perform(typeText("hasduhaodhasohdoas"), closeSoftKeyboard());
        onView(withId(R.id.btn_search)).perform(click());
        onView(withId(R.id.tv_account)).check(matches(withText("Account")));
        onView(withId(R.id.tv_name)).check(matches(withText("GitHub Name")));
        onView(withText("User not found")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

}
