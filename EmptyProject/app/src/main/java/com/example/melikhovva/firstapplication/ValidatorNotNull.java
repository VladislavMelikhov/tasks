package com.example.melikhovva.firstapplication;

public class ValidatorNotNull {

    public void argumentsValidation(final Object... objects) {

        if (objects.length > 0) {

            for (final Object object : objects) {
                if (object == null) {
                    throw new IllegalArgumentException("No value.");
                }
            }
        }
    }
}
