package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public final class ThreadCreator {

    public void startActionOnNewThread(final @NonNull Runnable action) {
        ValidatorNotNull.validateArguments(action);
        new Thread(action).start();
    }
}