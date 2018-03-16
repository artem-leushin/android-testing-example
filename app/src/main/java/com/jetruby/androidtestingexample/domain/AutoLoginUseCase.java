package com.jetruby.androidtestingexample.domain;

import com.jetruby.androidtestingexample.data.AuthDataSource;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by Artem Leushin on 08.02.2018.
 */

public class AutoLoginUseCase {

    final AuthDataSource authDataSource;

    @Inject
    public AutoLoginUseCase(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    public Completable tryAutoLogin() {
        return authDataSource.getUserEmail().toCompletable();
    }
}
