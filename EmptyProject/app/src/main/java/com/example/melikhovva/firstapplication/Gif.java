package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.io.Serializable;

public final class Gif implements Serializable {

    private final String name;
    private final String url;
    private final String id;

    public Gif(final @NonNull String name, final @NonNull String url, final @NonNull String id) {
        ValidatorNotNull.validateArguments(name, url, id);
        this.name = name;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }
}