package com.example.melikhovva.firstapplication;

public final class ValidatorNotNull {

    public void validateArguments(final Object... arguments) {

        if (arguments.length > 0) {

            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i] == null) {
                    throw new IllegalArgumentException("No value " + i + " argument");
                }
            }
        }
    }
}
