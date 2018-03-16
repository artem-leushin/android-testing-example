package com.jetruby.androidtestingexample.exception;

/**
 * Created by Artem Leushin on 12.02.2018.
 */

public class NoSuchUserException extends Exception {
    public NoSuchUserException(String message) {
        super(message);
    }
}
