package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public interface Optional<Content> {

    interface ActionWithContent<Content> {
        void receive(Content content);
    }

    boolean isExists();

    void doWithContentIfExists(ActionWithContent<Content> actionWithContent);
}

final class Existing<Content> implements Optional<Content> {

    private final Content content;

    public Existing(final @NonNull Content content) {
        ValidatorNotNull.validateArguments(content);
        this.content = content;
    }

    @Override
    public boolean isExists() {
        return true;
    }

    @Override
    public void doWithContentIfExists(final ActionWithContent<Content> actionWithContent) {
        actionWithContent.receive(content);
    }
}

final class NonExistent<Content> implements Optional<Content> {

    @Override
    public boolean isExists() {
        return false;
    }

    @Override
    public void doWithContentIfExists(final ActionWithContent<Content> actionWithContent) {

    }
}