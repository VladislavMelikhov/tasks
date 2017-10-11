package com.example.melikhovva.firstapplication;

import android.app.Activity;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public final class MainActivity extends Activity {
    private static final int COLUMN_COUNT = 4;
    private static final int SPACE_SIZE_DP = 2;


    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

                                    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.addItemDecoration(new SpacesItemDecoration((int) Math.ceil((double)gifs.size() / COLUMN_COUNT),
                                                                                            COLUMN_COUNT,
                                                                                            (int) getResources().getDisplayMetrics().density * SPACE_SIZE_DP));

                                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,
                                                                                        COLUMN_COUNT));

                                    recyclerView.setAdapter(new GifsAdapter(gifs));
                                }
                            });
                        }
                    });
                }
            }
        }).execute("http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC");
    }
}