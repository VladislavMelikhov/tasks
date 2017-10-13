package com.example.melikhovva.firstapplication;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public final class MainActivity extends Activity {

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_grid);


        new HttpRequest(new HttpRequestListener() {
            @Override
            public void interactionFinished(final HttpResponse response) {

                if (response.getResponseStatus() == ResponseStatus.Successful) {

                    final Optional<String> optionalResponseBody = response.getResponseBody();
                    optionalResponseBody.doWithContentIfExists(new Optional.ActionWithContent<String>() {
                        @Override
                        public void receive(final String body) {

                            final Optional<List<Gif>> trendGifs = new GifParser().parseTrending(body);
                            trendGifs.doWithContentIfExists(new Optional.ActionWithContent<List<Gif>>() {
                                @Override
                                public void receive(final List<Gif> gifs) {

                                    new RecyclerViewConfigurator().setGifGrid((RecyclerView) findViewById(R.id.recycler_view),
                                                                              gifs);
                                }
                            });
                        }
                    });
                }
            }
        }).execute("http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC");
    }
}