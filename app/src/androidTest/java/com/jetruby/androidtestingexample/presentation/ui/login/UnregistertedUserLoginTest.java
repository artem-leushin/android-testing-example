package com.jetruby.androidtestingexample.presentation.ui.login;

import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jetruby.androidtestingexample.R;
import com.jetruby.androidtestingexample.presentation.LoginActivity;
import com.jetruby.androidtestingexample.presentation.ui.UnregisteredRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by Artem Leushin on 11.01.2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UnregistertedUserLoginTest {

    private final String EMAIL = "example@jetruby.com";

    @Rule
    public UnregisteredRule<LoginActivity> activityTestRule = new UnregisteredRule<>(LoginActivity.class);

    private static Matcher<View> hasNoInput() {
        return new BoundedMatcher<View, EditText>(EditText.class) {
            @Override
            protected boolean matchesSafely(EditText item) {
                return item.getText().toString().length() == 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has no input");
            }
        };
    }

    private static Matcher<View> withProperHint(@StringRes int hintResId) {
        return new BoundedMatcher<View, EditText>(EditText.class) {
            @Override
            protected boolean matchesSafely(EditText item) {
                CharSequence displayedHint = item.getHint();
                CharSequence properHint = item.getContext().getString(hintResId);
                return displayedHint.equals(properHint);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has correct input hint");
            }
        };
    }

    private static Matcher<View> withProperError(@StringRes int errorResId) {
        return new BoundedMatcher<View, EditText>(EditText.class) {
            @Override
            protected boolean matchesSafely(EditText item) {
                CharSequence actualError = item.getError();
                CharSequence expectedError = item.getContext().getString(errorResId);
                return actualError.equals(expectedError);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has correct error text");
            }
        };
    }

    @Before
    public void setup() {
        Drawable notAnimatedDrawable = ContextCompat.getDrawable(activityTestRule.getActivity(), R.mipmap.ic_launcher);
        ((ProgressBar) activityTestRule.getActivity().findViewById(R.id.pb_progress))
                .setIndeterminateDrawable(notAnimatedDrawable);
    }

    @Test
    public void pressingLogin_emptyEmail_errorDisplayed() {
        onView(withId(R.id.et_email)).check(matches(hasNoInput()));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.et_email)).check(matches(withProperError(R.string.error_email_not_valid)));
    }

    @Test
    public void pressingSignup_emptyEmail_errorDisplayed() {
        onView(withId(R.id.et_email)).check(matches(hasNoInput()));
        onView(withId(R.id.btn_sign_up)).perform(click());
        onView(withId(R.id.et_email)).check(matches(withProperError(R.string.error_email_not_valid)));
    }


    @Test
    public void pressingLogin_userNotExists_errorDisplayed() {
        onView(withId(R.id.et_email)).perform(typeText(EMAIL), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("No user with such email")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void pressingSignup_userNotExists_progressDisplayed() {
        onView(withId(R.id.et_email)).perform(typeText(EMAIL), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_up)).perform(click());
        onView(withId(R.id.btn_sign_up)).check(matches(not(isDisplayed())));
        onView(withId(R.id.pb_progress)).check(matches(isDisplayed()));
    }

    @Test
    public void emptyEditText_properHintDisplayed() {
        onView(withId(R.id.et_email)).check(matches(hasNoInput()));
        onView(withId(R.id.et_email)).check(matches(withProperHint(R.string.hint_email)));
    }
}
