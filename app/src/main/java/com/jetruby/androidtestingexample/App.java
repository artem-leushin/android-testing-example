package com.jetruby.androidtestingexample;

import android.app.Application;

import com.jetruby.androidtestingexample.di.component.ApplicationComponent;
import com.jetruby.androidtestingexample.di.component.DaggerApplicationComponent;
import com.jetruby.androidtestingexample.di.module.ApplicationModule;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by Artem Leushin on 08.02.2018.
 */

public class App extends Application {

    public static final String PREFS_STORE = "prefs_store";

    static {
        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);
    }

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
