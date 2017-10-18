package com.example.melikhovva.firstapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

public final class SavedGifsActivity extends Activity {
    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_grid);

        new RecyclerViewConfigurator().setGifGrid((RecyclerView) findViewById(R.id.recycler_view),
                                                  new GifsStorageBuilder(this).build()
                                                                              .getGifs(),
                                                  new Scrollable() {
                                                      @Override
                                                      public void onScroll(final GifsAdapter adapter, final Runnable callback) {

                                                      }
                                                  });
    }
}