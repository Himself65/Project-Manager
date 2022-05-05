package com.coms3091mc3.projectmanager;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class TestMainActivity {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testClickAllNavigation() {
        onView(withId(R.id.navigation_dashboard))
                .perform(click());
        onView(withId(R.id.navigation_projects))
                .perform(click());
        onView(withId(R.id.navigation_inbox))
                .perform(click());
        onView(withId(R.id.navigation_settings))
                .perform(click());
    }
}
