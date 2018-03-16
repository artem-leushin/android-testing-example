package com.jetruby.androidtestingexample.presentation.ui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.jetruby.androidtestingexample.App;
import com.jetruby.androidtestingexample.presentation.LoginActivity;

import static com.jetruby.androidtestingexample.data.AuthDataSource.KEY_EMAIL;

/**
 * Created by Artem Leushin on 05.03.2018.
 */

public class RegisteredRule<T extends LoginActivity> extends IntentsTestRule<T> {

    public static final String EMAIL = "example@jetruby.com";

    public RegisteredRule(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        InstrumentationRegistry.getTargetContext()
                .getSharedPreferences(App.PREFS_STORE, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_EMAIL, EMAIL)
                .clear()
                .apply();
    }
}
