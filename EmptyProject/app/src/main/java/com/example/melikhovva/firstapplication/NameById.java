package com.example.melikhovva.firstapplication;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class NameById {

    private final SharedPreferences idsAndNames;

    public NameById(final @NonNull SharedPreferences idsAndNames) {
        ValidatorNotNull.validateArguments(idsAndNames);
        this.idsAndNames = idsAndNames;
    }

    public void putGifName(final @NonNull Gif gif) {
        ValidatorNotNull.validateArguments(gif);
        idsAndNames.edit()
                    .putString(gif.getId(), gif.getName())
                    .apply();
    }

    public boolean contains(final @NonNull String id) {
        ValidatorNotNull.validateArguments(id);
        return idsAndNames.contains(id);
    }

    public String getName(final @NonNull String id, final @Nullable String defaultValue) {
        ValidatorNotNull.validateArguments(id);
        return idsAndNames.getString(id, defaultValue);
    }
}