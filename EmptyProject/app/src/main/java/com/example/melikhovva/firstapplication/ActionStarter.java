package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public final class ActionStarter {

    public void startOnNewThread(final @NonNull Runnable action) {
        ValidatorNotNull.validateArguments(action);
        new Thread(action).start();
    }
}