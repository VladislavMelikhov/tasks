package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public final class GifsRequestor {

    private static final String ENCODING = "UTF-8";

    public void requestTrending(final int offset,
                                final int limit,
                                final @NonNull Optional.ActionWithContent<List<Gif>> actionWithGifs) {
        ValidatorNotNull.validateArguments(actionWithGifs);

        if (validateNonNegative(offset, limit)) {
            executeHttpRequest("http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC" +
                                       "&offset=" + offset +
                                       "&limit=" + limit,
                               actionWithGifs);
        }
    }

    public void requestForPhrase(final int limit,
                                 final @NonNull String phrase,
                                 final @NonNull Optional.ActionWithContent<List<Gif>> actionWithGifs) {
        ValidatorNotNull.validateArguments(phrase, actionWithGifs);

        if (validateNonNegative(limit)) {
            encodeString(phrase).doWithContentIfExists(new Optional.ActionWithContent<String>() {
                @Override
                public void receive(final String encodedPhrase) {
                    executeHttpRequest("http://api.giphy.com/v1/gifs/search?api_key=dc6zaTOxFJmzC" +
                                               "&limit=" + limit +
                                               "&q=" + encodedPhrase,
                                       actionWithGifs);
                }
            });
        }
    }

    private boolean validateNonNegative(final int... numbers) {
        for (int number : numbers) {
            if (number < 0) {
                return false;
            }
        }
        return true;
    }
    
    private Optional<String> encodeString(final String string) {

        try {
            return new Existing<>(URLEncoder.encode(string, ENCODING));

        } catch (final UnsupportedEncodingException e) {
            return new NonExistent<>();
        }
    }

    private void executeHttpRequest(final String url, final Optional.ActionWithContent<List<Gif>> actionWithGifs) {

        new HttpRequest(url, new HttpRequestListener() {
            @Override
            public void interactionFinished(final HttpResponse response) {

                if (response.getResponseStatus() == ResponseStatus.Successful) {

                    response.getResponseBody()
                            .doWithContentIfExists(new Optional.ActionWithContent<String>() {
                                @Override
                                public void receive(final String body) {

                                    InstancesHolder.getGifsConverter().convertFromJSON(body)
                                            .doWithContentIfExists(actionWithGifs);
                                }
                            });
                }
            }
        });
    }
}