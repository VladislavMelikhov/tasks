package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public final class EmptyGifDataLoader implements AdditionalGifDataLoader {
    @Override
    public void loadMore(final @NonNull GifsAdapter gifsAdapter, final int count) {

    }
}