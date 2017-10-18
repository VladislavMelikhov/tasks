package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;

public final class GifLoaderFactory {

    private final Context context;

    public GifLoaderFactory(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);
        this.context = context;
    }

    public GifLoader newInstance() {
        return new GlideGifLoader(context);
    }
}