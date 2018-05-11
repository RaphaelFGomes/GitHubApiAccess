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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PullRequestStateOpenClose {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void pullRequestStateOpenClose() {
        SystemClock.sleep(10000);
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerViewRepositories),
                        childAtPosition(
                                withId(R.id.linearLayoutMainActivity),
                                1)));
        SystemClock.sleep(10000);
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        SystemClock.sleep(10000);
        ViewInteraction toggleButton = onView(
                allOf(withId(R.id.toggleButtonState), withText("State: Open"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayoutPullActivity),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        SystemClock.sleep(10000);
        toggleButton.perform(click());

        SystemClock.sleep(10000);
        ViewInteraction toggleButton2 = onView(
                allOf(withId(R.id.toggleButtonState), withText("State: Close"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayoutPullActivity),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        SystemClock.sleep(10000);
        toggleButton2.perform(click());

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
