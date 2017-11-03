package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.util.List;

public final class SearchingGifsDataLoader {

    private final GifsRequestor gifsRequestor;

    public SearchingGifsDataLoader(final @NonNull GifsRequestor gifsRequestor) {
        ValidatorNotNull.validateArguments(gifsRequestor);
        this.gifsRequestor = gifsRequestor;
    }

    public void loadSearching(final int limit,
                              final @NonNull String phrase,
                              final @NonNull Optional.ActionWithContent<List<Gif>> actionWithGifs) {
        ValidatorNotNull.validateArguments(phrase, actionWithGifs);

        gifsRequestor.requestForPhrase(limit,
                                       phrase,
                                       new Optional.ActionWithContent<List<Gif>>() {
                                           @Override
                                           public void receive(final List<Gif> gifs) {
                                               actionWithGifs.receive(gifs);
                                           }
                                       });
    }
}