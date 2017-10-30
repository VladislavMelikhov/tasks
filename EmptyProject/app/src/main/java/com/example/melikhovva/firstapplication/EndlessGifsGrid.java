package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public final class EndlessGifsGrid extends GifsGrid {

    private int currentGifsCount = 0;

    public EndlessGifsGrid(final @NonNull RecyclerView recyclerView,
                           final @NonNull List<Gif> gifs,
                           final @NonNull AdditionalGifsDataLoader additionalGifsDataLoader) {
        super(recyclerView, gifs);
        ValidatorNotNull.validateArguments(additionalGifsDataLoader);

        currentGifsCount += gifs.size();
        recyclerView.addOnScrollListener(new OnDetectLastItemListener(gridLayoutManager) {
            @Override
            public void onDetectLastItem() {
                additionalGifsDataLoader.loadMore(currentGifsCount,
                                                  recyclerView.getResources().getInteger(R.integer.additional_loading_trending_gifs_count),
                                                  new Optional.ActionWithContent<List<Gif>>() {
                                                        @Override
                                                        public void receive(final List<Gif> gifs) {
                                                            currentGifsCount += gifs.size();
                                                            gifsAdapter.addAll(gifs);
                                                        }
                                                  });
            }
        });
    }
}