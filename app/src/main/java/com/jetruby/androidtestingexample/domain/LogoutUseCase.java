package com.jetruby.androidtestingexample.domain;

import com.jetruby.androidtestingexample.data.AuthDataSource;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by Artem Leushin on 09.02.2018.
 */

public class LogoutUseCase {

    final AuthDataSource authDataSource;

    @Inject
    public LogoutUseCase(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    public Completable logout() {
        return Completable.fromAction(authDataSource::clear);
    }

}
