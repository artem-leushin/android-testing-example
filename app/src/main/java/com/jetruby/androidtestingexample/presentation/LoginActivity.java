package com.jetruby.androidtestingexample.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jetruby.androidtestingexample.App;
import com.jetruby.androidtestingexample.R;
import com.jetruby.androidtestingexample.domain.LoginUseCase;
import com.jetruby.androidtestingexample.domain.SignUpUseCase;
import com.jetruby.androidtestingexample.exception.NoSuchUserException;
import com.jetruby.androidtestingexample.exception.UserAlreadyExistsException;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Artem Leushin on 08.02.2018.
 */

public class LoginActivity extends AppCompatActivity {

    @Inject
    LoginUseCase loginUseCase;
    @Inject
    SignUpUseCase signUpUseCase;

    private EditText etEmail;
    private Button btnLogin;
    private Button btnSignUp;
    private ProgressBar pbProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        App testingExampleApp = (App) getApplication();
        testingExampleApp.getApplicationComponent().inject(this);

        etEmail = findViewById(R.id.et_email);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sign_up);
        pbProgress = findViewById(R.id.pb_progress);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString();

            if (!validateEmail(email)) {
                etEmail.setError("This email is not valid");
                return;
            }

            loginUseCase.loginWithEmail(email)
                    .delay(2, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> {
                        setLoading(true);
                    })
                    .doFinally(() -> {
                        setLoading(false);
                    })
                    .doOnComplete(() -> {
                        startActivity(MainActivity.class);
                        finish();
                    })
                    .doOnError(throwable -> {
                        if (throwable instanceof NoSuchUserException) {
                            Snackbar.make(btnLogin, throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                        throwable.printStackTrace();
                    })
                    .subscribe();
        });


        btnSignUp.setOnClickListener((View view) -> {
            String email = etEmail.getText().toString();

            if (!validateEmail(email)) {
                etEmail.setError(getString(R.string.error_email_not_valid));
                return;
            }

            signUpUseCase.signUpWithEmail(email)
                    .delay(2, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> setLoading(true))
                    .doFinally(() -> setLoading(false))
                    .doOnComplete(() -> {
                        Snackbar.make(btnSignUp, R.string.message_success_signup, Snackbar.LENGTH_SHORT).show();
                        etEmail.setText(null);
                    })
                    .doOnError(throwable -> {
                        if (throwable instanceof UserAlreadyExistsException) {
                            Snackbar.make(btnSignUp, throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                        throwable.printStackTrace();
                    })
                    .subscribe();

        });

    }

    private void setLoading(boolean loading) {
        if (loading) {
            btnLogin.setVisibility(View.INVISIBLE);
            btnSignUp.setVisibility(View.INVISIBLE);
            pbProgress.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            btnSignUp.setVisibility(View.VISIBLE);
            pbProgress.setVisibility(View.INVISIBLE);
        }
    }

    private void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
