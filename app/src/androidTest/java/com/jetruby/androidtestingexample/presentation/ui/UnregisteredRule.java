package com.jetruby.androidtestingexample.presentation.ui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.jetruby.androidtestingexample.App;
import com.jetruby.androidtestingexample.presentation.LoginActivity;

/**
 * Created by Artem Leushin on 05.03.2018.
 */

public class UnregisteredRule<T extends LoginActivity> extends ActivityTestRule<T> {

    public UnregisteredRule(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        InstrumentationRegistry.getTargetContext()
                .getSharedPreferences(App.PREFS_STORE, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
}
