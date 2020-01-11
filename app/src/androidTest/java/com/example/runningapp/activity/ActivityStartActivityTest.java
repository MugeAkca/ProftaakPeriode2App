package com.example.runningapp.activity;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import com.example.runningapp.R;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ActivityStartActivityTest {

    @Rule
    public ActivityTestRule<ActivityStartActivity> mActivityTestRule = new ActivityTestRule<ActivityStartActivity>(ActivityStartActivity.class);

    private ActivityStartActivity mStartActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityEndActivity.class.getName(), null, false);



    @Before
    public void setUp() throws Exception {
        mStartActivity = mActivityTestRule.getActivity();
    }

    // testLaunchOfSecondActivityOnButton
    @Test
    public void testLaunchOfSecondActivityOnButton(){
        assertNotNull(mStartActivity.findViewById(R.id.btnEndActivity));

        onView(withId(R.id.btnEndActivity)).perform(click());

        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 100000000);

        assertNotNull(secondActivity);

        secondActivity.finish();




    }
    @Test
    public void testLaunch(){

        View view = mStartActivity.findViewById(R.id.lblLocation);

        assertNotNull(view);

    }
    @After
    public void tearDown() throws Exception {
        mStartActivity = null;

    }

    @Test
    public void onCreate() {
    }
}