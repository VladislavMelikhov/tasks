package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public final class Gif {

    private final String name;
    private final String url;

    public Gif(final @NonNull String name, final @NonNull String url) {
        ValidatorNotNull.validateArguments(name, url);
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