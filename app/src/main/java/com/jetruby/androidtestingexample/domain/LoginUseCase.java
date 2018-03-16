package com.jetruby.androidtestingexample.domain;

import com.jetruby.androidtestingexample.data.AuthDataSource;
import com.jetruby.androidtestingexample.exception.NoSuchUserException;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by Artem Leushin on 08.02.2018.
 */

public class LoginUseCase {

    final AuthDataSource authDataSource;

    @Inject
    public LoginUseCase(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    public Completable loginWithEmail(String email) {
        return authDataSource.getUserEmail()
                .flatMapCompletable(storedEmail -> {
                    if (storedEmail.equals(email)) {
                        return Completable.complete();
                    }
                    return Completable.error(new NoSuchUserException("No user with such email"));
                });
    }

}
