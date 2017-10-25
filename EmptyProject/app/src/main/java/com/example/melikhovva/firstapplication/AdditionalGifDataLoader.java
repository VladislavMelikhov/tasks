package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public interface AdditionalGifDataLoader {

    void loadMore(@NonNull GifsAdapter gifsAdapter, int count);
}