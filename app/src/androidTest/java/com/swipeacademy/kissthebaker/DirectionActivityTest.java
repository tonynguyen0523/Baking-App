package com.swipeacademy.kissthebaker;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.swipeacademy.kissthebaker.BakingInstructions.DirectionsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by tonyn on 8/6/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DirectionActivityTest {

    private int pageNumber;
    private int totalPages;

    @Before
    public void currentPageNumber(){
    }

    @Rule
    public ActivityTestRule<DirectionsActivity> mActivityTestRule =
            new ActivityTestRule<DirectionsActivity>(DirectionsActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent results = new Intent(targetContext,DirectionsActivity.class);
                    return super.getActivityIntent();
                }
            };

    @Test
    public void navigationBarText(){

        onView((withId(R.id.step_number))).check(matches(withText("3")));
    }
}
