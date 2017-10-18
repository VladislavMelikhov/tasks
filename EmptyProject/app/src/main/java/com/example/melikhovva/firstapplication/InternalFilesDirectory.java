package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;

public final class InternalFilesDirectory implements Directory {

    private final File directory;

    public InternalFilesDirectory(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);
        directory = context.getFilesDir();
    }

    @Override
    public File getDirectory() {
        return directory;
    }
}