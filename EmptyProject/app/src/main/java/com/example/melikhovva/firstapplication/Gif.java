package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public final class Gif {

    private final String name;
    private final String url;

    public Gif(final @NonNull String name, final @NonNull String url) {
        new ValidatorNotNull().argumentsValidation(name, url);
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}