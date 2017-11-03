package com.example.melikhovva.firstapplication;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public final class MainActivity extends ToolsBackedActivity {

    private final GifsRequestor gifsRequestor;
    private final GifLoader gifLoader;

    public MainActivity() {
        gifsRequestor = applicationToolsProvider.getGifsRequestor();
        gifLoader = applicationToolsProvider.getGifLoader();
    }

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolBar(getString(R.string.main_activity_toolbar_text));

        gifsRequestor.requestTrending(0,
                                      getResources().getInteger(R.integer.initial_trending_gifs_count),
                                      new Optional.ActionWithContent<List<Gif>>() {
                                          @Override
                                          public void receive(final List<Gif> gifs) {
                                              new EndlessGifsGrid((RecyclerView) findViewById(R.id.recycler_view),
                                                                  gifs,
                                                                  gifLoader,
                                                                  new AdditionalGifsDataLoader(gifsRequestor));
                                          }
                                      });
    }

    private void configureToolBar(final String title) {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                goToSearchableActivity();
            }
        });
    }

    private void goToSearchableActivity() {
        MainActivity.this.startActivity(new Intent(MainActivity.this,
                                                   SearchableActivity.class));
    }
}