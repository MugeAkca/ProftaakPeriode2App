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
public class ActivityEndActivityTest {

    @Rule
    public ActivityTestRule<ActivityEndActivity> mActivityTestRule = new ActivityTestRule<ActivityEndActivity>(ActivityEndActivity.class);

    private ActivityEndActivity mStartActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityStartActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        mStartActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfCancelActivityOnButton(){

        try {
            assertNotNull(mStartActivity.findViewById(R.id.btnCancelActivity));

            onView(withId(R.id.btnCancelActivity)).perform(click());

            Activity CancelActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 100000000);

            assertNotNull(CancelActivity);

            CancelActivity.finish();

        }catch(NumberFormatException e){
            
        }

    }

    @After
    public void tearDown() throws Exception {
        mStartActivity = null;

    }
}