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

        new GifsRequestor().requestTrending(0, getResources().getInteger(R.integer.initial_gifs_count), new Optional.ActionWithContent<List<Gif>>() {
            @Override
            public void receive(final List<Gif> gifs) {
                new RecyclerViewConfigurator().setGifGrid((RecyclerView) findViewById(R.id.recycler_view),
                                                          gifs,
                                                          new AdditionalGifDataRequestor());
            }
        });
    }
}