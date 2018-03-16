package com.jetruby.androidtestingexample.di.component;

import com.jetruby.androidtestingexample.di.module.ApplicationModule;
import com.jetruby.androidtestingexample.di.module.DataSourceModule;
import com.jetruby.androidtestingexample.presentation.EnterActivity;
import com.jetruby.androidtestingexample.presentation.LoginActivity;
import com.jetruby.androidtestingexample.presentation.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Artem Leushin on 08.02.2018.
 */

@Singleton
@Component(modules = {
        DataSourceModule.class,
        ApplicationModule.class
})
public interface ApplicationComponent {

    void inject(LoginActivity loginActivity);

    void inject(EnterActivity enterActivity);

    void inject(MainActivity mainActivity);
}
