package com.jetruby.androidtestingexample.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jetruby.androidtestingexample.App;
import com.jetruby.androidtestingexample.domain.AutoLoginUseCase;
import com.jetruby.androidtestingexample.exception.NoSuchUserException;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by Artem Leushin on 09.02.2018.
 */

public class EnterActivity extends AppCompatActivity {

    @Inject
    AutoLoginUseCase autoLoginUseCase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App exampleApp = (App) getApplication();
        exampleApp.getApplicationComponent().inject(this);

        autoLoginUseCase.tryAutoLogin().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                startActivity(MainActivity.class);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof NoSuchUserException) {
                    startActivity(LoginActivity.class);
                } else {
                    throw new RuntimeException();
                }
            }
        });

        finish();
    }

    private void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
