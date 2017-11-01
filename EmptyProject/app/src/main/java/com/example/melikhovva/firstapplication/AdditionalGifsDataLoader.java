package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.util.List;

public final class AdditionalGifsDataLoader {

    private boolean loadingAllowed = true;

    public void loadMore(final int offset,
                         final int limit,
                         final @NonNull Optional.ActionWithContent<List<Gif>> actionWithGifs) {
        ValidatorNotNull.validateArguments(actionWithGifs);

        if (loadingAllowed) {
            loadingAllowed = false;
            InstancesHolder.getGifsRequestor().requestTrending(offset,
                                                               limit,
                                                               new Optional.ActionWithContent<List<Gif>>() {
                                                                   @Override
                                                                   public void receive(final List<Gif> gifs) {
                                                                       actionWithGifs.receive(gifs);
                                                                       loadingAllowed = true;
                                                                   }
                                                               });
        }
    }
}