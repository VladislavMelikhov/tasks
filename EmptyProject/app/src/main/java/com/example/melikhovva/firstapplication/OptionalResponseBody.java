package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public interface OptionalResponseBody {

    interface ActionWithBody {
        void receive(String body);
    }

    boolean isExists();

    void doWithBodyIfExists(ActionWithBody actionWithBody);
}

final class NotExistResponseBody implements OptionalResponseBody {

    @Override
    public boolean isExists() {
        return false;
    }

    @Override
    public void doWithBodyIfExists(final ActionWithBody actionWithBody) {

    }
}

final class ExistResponseBody implements OptionalResponseBody {

    private final String body;

    @Override
    public boolean isExists() {
        return true;
    }

    public ExistResponseBody(final @NonNull String body) {
        if (body == null) {
            throw new IllegalArgumentException("No value.");
        }
        this.body = body;
    }

    @Override
    public void doWithBodyIfExists(final ActionWithBody actionWithBody) {
        actionWithBody.receive(body);
    }
}