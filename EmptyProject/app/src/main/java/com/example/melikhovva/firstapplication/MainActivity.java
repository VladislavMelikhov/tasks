package com.example.melikhovva.firstapplication;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public final class MainActivity extends Activity {

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_grid);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        new RecyclerViewConfigurator().setGifGrid(recyclerView,
                                                  new ArrayList<Gif>());



        final GifsAdapter adapter = (GifsAdapter) recyclerView.getAdapter();

        doWithGifsIfLoaded(adapter.getItemCount(), getResources().getInteger(R.integer.initial_gifs_count), new Optional.ActionWithContent<List<Gif>>() {
            @Override
            public void receive(final List<Gif> gifs) {
                adapter.addAll(gifs);
            }
        });

        recyclerView.addOnScrollListener(new EndlessScrollListener((GridLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void loadMore() {

                doWithGifsIfLoaded(adapter.getItemCount(), getResources().getInteger(R.integer.loading_gifs_count), new Optional.ActionWithContent<List<Gif>>() {
                    @Override
                    public void receive(final List<Gif> gifs) {
                        adapter.addAll(gifs);
                        loadingFinished();
                    }
                });
            }
        });
    }

    private void doWithGifsIfLoaded(final int offset, final int limit, final Optional.ActionWithContent<List<Gif>> actionWithGifs) {

        new HttpRequest(new HttpRequestListener() {
            @Override
            public void interactionFinished(final HttpResponse response) {

                if (response.getResponseStatus() == ResponseStatus.Successful) {

                    final Optional<String> optionalResponseBody = response.getResponseBody();
                    optionalResponseBody.doWithContentIfExists(new Optional.ActionWithContent<String>() {
                        @Override
                        public void receive(final String body) {

                            final Optional<List<Gif>> trendGifs = new GifParser().parseTrending(body);
                            trendGifs.doWithContentIfExists(actionWithGifs);
                        }
                    });
                }
            }
        }).execute("http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC" +
                           "&limit=" + limit +
                           "&offset=" + offset);
    }
}