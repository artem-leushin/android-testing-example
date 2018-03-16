package com.jetruby.androidtestingexample.data;

import io.reactivex.Single;

/**
 * Created by Artem Leushin on 08.02.2018.
 */

public interface AuthDataSource {

    String KEY_EMAIL = "key_email";

    Single<String> getUserEmail();

    void persistEmail(String email);

    void clear();
}
