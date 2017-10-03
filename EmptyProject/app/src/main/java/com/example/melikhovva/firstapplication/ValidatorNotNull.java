package com.example.melikhovva.firstapplication;

public final class ValidatorNotNull {

    public void validateArguments(final Object... arguments) {

        if (arguments.length > 0) {

            // TODO: print the numper of null argument
            for (final Object argument : arguments) {
                if (argument == null) {
                    throw new IllegalArgumentException("No value.");
                }
            }
        }
    }
}
