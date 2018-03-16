package com.jetruby.androidtestingexample.exception;

/**
 * Created by Artem Leushin on 12.02.2018.
 */

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
