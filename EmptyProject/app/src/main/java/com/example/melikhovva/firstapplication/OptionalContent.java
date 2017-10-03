package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public interface OptionalContent<ContentType> {

    interface ActionWithContent<ReceivedType> {
        void receive(ReceivedType content);
    }

    boolean isExists();

    void doWithContentIfExists(ActionWithContent<ContentType> actionWithContent);
}

final class ExistContent<ContentType> implements OptionalContent<ContentType> {

    private final ContentType content;

    public ExistContent(final @NonNull ContentType content) {
        new ValidatorNotNull().argumentsValidation(content);
        this.content = content;
    }

    @Override
    public boolean isExists() {
        return true;
    }

    @Override
    public void doWithContentIfExists(final ActionWithContent<ContentType> actionWithContent) {
        actionWithContent.receive(content);
    }
}

final class NotExistContent<ContentType> implements OptionalContent<ContentType> {

    @Override
    public boolean isExists() {
        return false;
    }

    @Override
    public void doWithContentIfExists(final ActionWithContent<ContentType> actionWithContent) {

    }
}