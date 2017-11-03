package com.example.melikhovva.firstapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public final class SavedGifsActivity extends ToolsBackedActivity {

    private final GifsStorage gifsStorage;
    private final GifLoader gifLoader;

    public SavedGifsActivity() {
        gifsStorage = applicationToolsProvider.getGifsStorage();
        gifLoader = applicationToolsProvider.getGifLoader();
    }

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        gifsStorage.doWithGifsIfExists(new Optional.ActionWithContent<List<Gif>>() {
            @Override
            public void receive(final List<Gif> gifs) {
                new GifsGrid((RecyclerView) findViewById(R.id.recycler_view),
                             gifs,
                             gifLoader);
            }
        });
    }
}