package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class GifsGrid {

    protected final RecyclerView recyclerView;
    protected final GridLayoutManager gridLayoutManager;
    protected final GifsAdapter gifsAdapter;

    public GifsGrid(final @NonNull RecyclerView recyclerView,
                    final @NonNull List<Gif> gifs,
                    final @NonNull GifLoader gifLoader) {
        ValidatorNotNull.validateArguments(recyclerView, gifs, gifLoader);
        ValidatorNotNull.validateArguments(gifs.toArray());

        this.recyclerView = recyclerView;

        recyclerView.setHasFixedSize(true);

        final Context context = recyclerView.getContext();
        final Resources resources = context.getResources();
        final int columnCount = resources.getInteger(R.integer.column_count);

        gridLayoutManager = new GridLayoutManager(context,
                                                  columnCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        gifsAdapter = new GifsAdapter(gifs, gifLoader);
        recyclerView.setAdapter(gifsAdapter);

        recyclerView.addItemDecoration(new SpacesItemDecoration(columnCount,
                                                                resources.getDimensionPixelOffset(R.dimen.space_size_dp)));
    }
}