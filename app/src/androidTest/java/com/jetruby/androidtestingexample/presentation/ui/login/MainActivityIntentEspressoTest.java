package com.jetruby.androidtestingexample.presentation.ui.login;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.jetruby.androidtestingexample.R;
import com.jetruby.androidtestingexample.presentation.LoginActivity;
import com.jetruby.androidtestingexample.presentation.MainActivity;
import com.jetruby.androidtestingexample.presentation.ui.RegisteredRule;

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

/**
 * Created by Artem Leushin on 11.01.2018.
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class MainActivityIntentEspressoTest {

    @Rule
    public RegisteredRule<LoginActivity> signedUpRule = new RegisteredRule<>(LoginActivity.class);

    @Test
    public void mainActivityIsLaunchedAfterLoginValidation() {
        onView(withId(R.id.et_email))
                .perform(typeText(EMAIL), closeSoftKeyboard());

        onView(withId(R.id.btn_login)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void logoTextIsCorrect() {
        Espresso.onView(withId(R.id.tv_robo_title)).check(matches(withText(R.string.app_name)));
    }

    @Test
    public void logoIsDisplayed() {
        Espresso.onView(withId(R.id.tv_robo_title)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.iv_robo_logo)).check(matches(isDisplayed()));
    }
}
