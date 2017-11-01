package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.util.List;

public final class SearchingGifsDataLoader {

    public void loadSearching(final int limit,
                              final @NonNull String phrase,
                              final @NonNull Optional.ActionWithContent<List<Gif>> actionWithGifs) {
        ValidatorNotNull.validateArguments(phrase, actionWithGifs);

        InstancesHolder.getGifsRequestor().requestForPhrase(limit,
                                                            phrase,
                                                            new Optional.ActionWithContent<List<Gif>>() {
                                                                @Override
                                                                public void receive(final List<Gif> gifs) {
                                                                    actionWithGifs.receive(gifs);
                                                                }
                                                            });
    }
}