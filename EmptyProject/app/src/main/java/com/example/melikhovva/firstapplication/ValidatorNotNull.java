package com.example.melikhovva.firstapplication;

public final class ValidatorNotNull {

    private ValidatorNotNull(){}

    public static void validateArguments(final Object... arguments) {

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] == null) {
                throw new IllegalArgumentException("No value " + i + " argument");
            }
        }
    }
}
