package com.jetruby.androidtestingexample.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.jetruby.androidtestingexample.App;
import com.jetruby.androidtestingexample.data.AuthDataLocalStore;
import com.jetruby.androidtestingexample.data.AuthDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Artem Leushin on 08.02.2018.
 */

@Module
public class ApplicationModule {

    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(App.PREFS_STORE, Context.MODE_PRIVATE);
    }

    @Provides
    AuthDataSource provideAuthDataSource(AuthDataLocalStore authDataLocalStore) {
        return authDataLocalStore;
    }
}
