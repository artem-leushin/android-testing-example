package com.jetruby.androidtestingexample.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.jetruby.androidtestingexample.App;
import com.jetruby.androidtestingexample.R;
import com.jetruby.androidtestingexample.domain.DisplayUserEmailUseCase;
import com.jetruby.androidtestingexample.domain.LogoutUseCase;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    LogoutUseCase logoutUseCase;
    @Inject
    DisplayUserEmailUseCase displayUserEmailUseCase;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App exampleApp = (App) getApplication();
        exampleApp.getApplicationComponent().inject(this);

        this.tvEmail = findViewById(R.id.tv_email);

        this.displayUserEmailUseCase.getUserEmail()
                .doOnSuccess(email -> tvEmail.setText(email))
                .subscribe();

        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(view -> logoutUseCase.logout()
                .doOnComplete(() -> {
                    startActivity(LoginActivity.class);
                    finish();
                })
                .subscribe());
    }

    private void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
