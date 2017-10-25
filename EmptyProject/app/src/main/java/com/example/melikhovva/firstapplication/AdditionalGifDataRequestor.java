package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.util.List;

public final class AdditionalGifDataRequestor implements AdditionalGifDataLoader {

    private LoadingStatus loadingStatus = LoadingStatus.Allowed;

    private void loadingStarted() {
        loadingStatus = LoadingStatus.Banned;
    }

    private void loadingFinished() {
        loadingStatus = LoadingStatus.Allowed;
    }

    @Override
    public void loadMore(final @NonNull GifsAdapter gifsAdapter, final int count) {
        ValidatorNotNull.validateArguments(gifsAdapter);

        if (loadingStatus == LoadingStatus.Allowed) {
            loadingStarted();
            new GifsRequestor().requestTrending(gifsAdapter.getItemCount(),
                                                count,
                                                new Optional.ActionWithContent<List<Gif>>() {
                                                    @Override
                                                    public void receive(final List<Gif> gifs) {
                                                        gifsAdapter.addAll(gifs);
                                                        loadingFinished();
                                                    }
                                                });
        }
    }
}