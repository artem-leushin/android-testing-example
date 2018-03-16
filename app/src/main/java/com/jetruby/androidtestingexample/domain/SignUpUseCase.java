package com.jetruby.androidtestingexample.domain;

import com.jetruby.androidtestingexample.data.AuthDataSource;
import com.jetruby.androidtestingexample.exception.NoSuchUserException;
import com.jetruby.androidtestingexample.exception.UserAlreadyExistsException;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by Artem Leushin on 09.02.2018.
 */

public class SignUpUseCase {

    final AuthDataSource authDataSource;

    @Inject
    public SignUpUseCase(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    public Completable signUpWithEmail(String email) {
        return authDataSource.getUserEmail()
                .flatMapCompletable(storedEmail -> {
                    if (storedEmail.equals(email)) {
                        return Completable.error(new UserAlreadyExistsException("User already exists"));
                    }
                    return Completable.complete();
                })
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof NoSuchUserException) {
                        return Completable.fromAction(() -> authDataSource.persistEmail(email));
                    }
                    return Completable.error(throwable);
                });
    }
}
