package com.jetruby.androidtestingexample.data;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jetruby.androidtestingexample.exception.NoSuchUserException;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Artem Leushin on 08.02.2018.
 */

public class AuthDataLocalStore implements AuthDataSource {

    final SharedPreferences sharedPreferences;

    @Inject
    public AuthDataLocalStore(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Single<String> getUserEmail() {
        return Single.create(emitter -> {
            try {
                String email = sharedPreferences.getString(KEY_EMAIL, "");
                if (!TextUtils.isEmpty(email)) {
                    emitter.onSuccess(email);
                } else {
                    emitter.onError(new NoSuchUserException("No user with such email"));
                }
            } catch (Throwable t) {
                emitter.onError(t);
            }
        });
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void persistEmail(String email) {
        this.sharedPreferences.edit().putString(KEY_EMAIL, email).commit();
    }


    @SuppressLint("ApplySharedPref")
    @Override
    public void clear() {
        this.sharedPreferences.edit().clear().commit();
    }

}
