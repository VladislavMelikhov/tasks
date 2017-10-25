package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.io.File;

public final class Directory {

    private final File directory;

    public Directory(final @NonNull File directory) {
        ValidatorNotNull.validateArguments(directory);
        validateDirectory(directory);
        this.directory = directory;
    }

    private void validateDirectory(final File pathname) {
        if (!pathname.isDirectory()) {
            throw new IllegalArgumentException("The file denoted by this pathname is not a directory");
        }
    }

    public File getDirectory() {
        return directory;
    }
}