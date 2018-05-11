package com.gomes.ferreira.raphael.githubapiaccess.View;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.gomes.ferreira.raphael.githubapiaccess.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainFlow {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainFlow() {
        SystemClock.sleep(10000);
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerViewRepositories),
                        childAtPosition(
                                withId(R.id.linearLayoutMainActivity),
                                1)));
        SystemClock.sleep(10000);
        recyclerView.perform(actionOnItemAtPosition(2, click()));
        SystemClock.sleep(10000);
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerViewPulls),
                        childAtPosition(
                                withId(R.id.linearLayoutPullActivity),
                                0)));
        SystemClock.sleep(10000);
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
