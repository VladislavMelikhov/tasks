package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;

public final class StringsPreferencesCreator {

    private final Context context;

    public StringsPreferencesCreator(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);
        this.context = context;
    }
    public StringsPreferences create(final @NonNull String filename) {
        return new StringsPreferences(context.getSharedPreferences(filename,
                                                                   Context.MODE_PRIVATE));
    }
}