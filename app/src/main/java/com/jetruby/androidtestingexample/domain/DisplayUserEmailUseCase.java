package com.jetruby.androidtestingexample.domain;

import com.jetruby.androidtestingexample.data.AuthDataSource;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Artem Leushin on 12.02.2018.
 */

public class DisplayUserEmailUseCase {

    final AuthDataSource authDataSource;

    @Inject
    public DisplayUserEmailUseCase(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    public Single<String> getUserEmail() {
        return authDataSource.getUserEmail();
    }
}
