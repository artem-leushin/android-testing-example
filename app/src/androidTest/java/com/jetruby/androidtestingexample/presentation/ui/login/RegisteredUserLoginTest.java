package com.jetruby.androidtestingexample.presentation.ui.login;

import android.graphics.drawable.Drawable;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

import com.jetruby.androidtestingexample.R;
import com.jetruby.androidtestingexample.presentation.LoginActivity;
import com.jetruby.androidtestingexample.presentation.ui.RegisteredRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jetruby.androidtestingexample.presentation.ui.RegisteredRule.EMAIL;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by Artem Leushin on 05.03.2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisteredUserLoginTest {

    @Rule
    public RegisteredRule<LoginActivity> activityTestRule = new RegisteredRule<>(LoginActivity.class);

    @Before
    public void setup() {
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityTestRule.getActivity(), R.mipmap.ic_launcher);
        ((ProgressBar) activityTestRule.getActivity().findViewById(R.id.pb_progress))
                .setIndeterminateDrawable(notAnimatedDrawable);
    }

    @Test
    public void pressingLogin_userExists_progressDisplayed() {
        onView(withId(R.id.et_email)).perform(typeText(EMAIL), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).perform(click());

        onView(withId(R.id.pb_progress)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(not(isDisplayed())));
    }

    @Test
    public void pressingSignup_userExists_errorDisplayed() {
        onView(withId(R.id.et_email)).perform(typeText(EMAIL), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_up)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("User already exists")))
                .check(matches(isDisplayed()));
    }
}
