package com.example.melikhovva.firstapplication;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public final class StringsPreferences {

    private final SharedPreferences sharedPreferences;

    public StringsPreferences(final @NonNull SharedPreferences sharedPreferences) {
        ValidatorNotNull.validateArguments(sharedPreferences);
        this.sharedPreferences = sharedPreferences;
    }

    public void putString(final @NonNull String key, final @NonNull String string) {
        ValidatorNotNull.validateArguments(key, string);
        sharedPreferences.edit()
                        .putString(key, string)
                        .apply();
    }

    public Optional<String> getString(final @NonNull String key) {
        ValidatorNotNull.validateArguments(key);
        final String string = sharedPreferences.getString(key, null);

        if (string != null) {
            return new Existing<>(string);

        } else {
            return new NonExistent<>();
        }
    }
}