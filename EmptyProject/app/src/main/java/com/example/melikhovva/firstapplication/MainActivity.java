package com.example.melikhovva.firstapplication;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Collection;

public final class MainActivity extends Activity {
    private static final String MY_TAG = "myLogsMain";

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new HttpRequest(new HttpRequestListener() {
            @Override
            public void interactionFinished(final HttpResponse response) {

                if (response.getResponseStatus() == ResponseStatus.Successfull) {

                    final OptionalContent<String> optionalResponseBody = response.getResponseBody();
                    optionalResponseBody.doWithContentIfExists(new OptionalContent.ActionWithContent<String>() {
                        @Override
                        public void receive(final String body) {

                            final OptionalContent<Collection<Gif>> trendGifs = new GifParser().parseTrending(body);
                            trendGifs.doWithContentIfExists(new OptionalContent.ActionWithContent<Collection<Gif>>() {
                                @Override
                                public void receive(final Collection<Gif> gifs) {

                                    for (final Gif gif : gifs) {
                                        Log.d(MY_TAG, gif.getName() + " " + gif.getUrl());
                                    }
                                }
                            });
                            if (!trendGifs.isExists()) {
                                Log.d(MY_TAG, "JSON string is incorrect");
                            }
                        }
                    });
                    if (!optionalResponseBody.isExists()) {
                        Log.d(MY_TAG, "Empty body");
                    }
                } else {
                    Log.d(MY_TAG, response.getResponseStatus().toString());
                }
            }
        }).execute("http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC");
    }
}