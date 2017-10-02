package com.example.melikhovva.firstapplication;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Collection;

public final class MainActivity extends Activity {
    private static final String MY_TAG = "myLogs";

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new HttpRequest(new HttpRequestListener() {
            @Override
            public void interactionFinished(final HttpResponse response) {

                if (response.getResponseStatus() == ResponseStatus.Successfull) {

                    response.getResponseBody().doWithBodyIfExists(new OptionalResponseBody.ActionWithBody() {
                        @Override
                        public void receive(final String body) {

                            final OptionalGifs trendGifs = new JSONParser().parseTrendGifs(body);
                            trendGifs.doWithGifsIfExists(new OptionalGifs.ActionWithGifs(){
                                @Override
                                public void receive(Collection<Gif> gifs) {
                                    for (Gif gif : gifs) {
                                        Log.d(MY_TAG, gif.getName() + " " + gif.getUrl());
                                    }
                                }
                            });
                            if (!trendGifs.isExists()) {
                                Log.d(MY_TAG, "JSON string is incorrect");
                            }
                        }
                    });
                    if (!response.getResponseBody().isExists()) {
                        Log.d(MY_TAG, "Empty body");
                    }
                } else {
                    Log.d(MY_TAG, response.getResponseStatus().toString());
                }
            }
        }).execute("http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC");
    }
}