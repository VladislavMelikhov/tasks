package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.util.List;

public final class SearchingGifsGrid extends GifsGrid {

    public SearchingGifsGrid(final @NonNull RecyclerView recyclerView,
                             final @NonNull List<Gif> gifs,
                             final @NonNull GifLoader gifLoader,
                             final @NonNull SearchView searchView,
                             final @NonNull SearchingGifsDataLoader searchingGifsDataLoader) {
        super(recyclerView, gifs, gifLoader);
        ValidatorNotNull.validateArguments(searchView, searchingGifsDataLoader);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(final String query) {

                searchingGifsDataLoader.loadSearching(recyclerView.getResources().getInteger(R.integer.searching_gifs_count),
                                                      query,
                                                      new Optional.ActionWithContent<List<Gif>>() {
                                                          @Override
                                                          public void receive(final List<Gif> gifs) {
                                                              gifsAdapter.clear();
                                                              gifsAdapter.addAll(gifs);
                                                          }
                                                      });
                return true;
            }
        });
    }
}