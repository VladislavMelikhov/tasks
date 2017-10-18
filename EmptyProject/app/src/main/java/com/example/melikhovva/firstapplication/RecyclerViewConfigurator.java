package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public final class RecyclerViewConfigurator {

    public void setGifGrid(final @NonNull RecyclerView recyclerView, final @NonNull List<Gif> gifs, final @NonNull Scrollable scrollable) {
        ValidatorNotNull.validateArguments(recyclerView, gifs, scrollable);
        ValidatorNotNull.validateArguments(gifs.toArray());

        recyclerView.setHasFixedSize(true);

        final Context context = recyclerView.getContext();
        final Resources resources = context.getResources();
        final int columnCount = resources.getInteger(R.integer.column_count);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context,
                                                                          columnCount);
        final GifsAdapter gifsAdapter = new GifsAdapter(gifs);

        recyclerView.addItemDecoration(new SpacesItemDecoration(columnCount,
                                                                resources.getDimensionPixelOffset(R.dimen.space_size_dp)));
        recyclerView.setAdapter(gifsAdapter);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void loadMore() {
                scrollable.onScroll(gifsAdapter, new Runnable(){
                    @Override
                    public void run() {
                        loadingFinished();
                    }
                });
            }
        });
    }
}