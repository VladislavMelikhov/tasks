package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public final class RecyclerViewConfigurator {

    public void setGifGrid(final @NonNull RecyclerView recyclerView,
                           final @NonNull List<Gif> gifs,
                           final @NonNull AdditionalGifDataLoader additionalGifDataLoader) {

        ValidatorNotNull.validateArguments(recyclerView, gifs, additionalGifDataLoader);
        ValidatorNotNull.validateArguments(gifs.toArray());

        recyclerView.setHasFixedSize(true);

        final Context context = recyclerView.getContext();
        final Resources resources = context.getResources();
        final int columnCount = resources.getInteger(R.integer.column_count);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context,
                                                                          columnCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        final GifsAdapter gifsAdapter = new GifsAdapter(gifs);
        recyclerView.setAdapter(gifsAdapter);

        recyclerView.addItemDecoration(new SpacesItemDecoration(columnCount,
                                                                resources.getDimensionPixelOffset(R.dimen.space_size_dp)));

        recyclerView.addOnScrollListener(new OnDetectLastItemListener(gridLayoutManager) {
            @Override
            public void onDetectLastItem() {
                additionalGifDataLoader.loadMore(gifsAdapter,
                                                 resources.getInteger(R.integer.loading_gifs_count));
            }
        });
    }
}