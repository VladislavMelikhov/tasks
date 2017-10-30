package com.example.melikhovva.firstapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public final class SearchableActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        configureToolBar();

        final SearchView searchView = (SearchView) findViewById(R.id.search_view);
        reduceElementOfSearchView(searchView, android.support.v7.appcompat.R.id.search_mag_icon);
        reduceElementOfSearchView(searchView, android.support.v7.appcompat.R.id.search_close_btn);
        searchView.setIconifiedByDefault(false);

        new SearchingGifsGrid((RecyclerView) findViewById(R.id.recycler_view),
                              new ArrayList<Gif>(),
                              searchView,
                              new SearchingGifsDataLoader());
    }

    private void configureToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void reduceElementOfSearchView(final SearchView searchView, final int id) {
        ImageView imageView = (ImageView) searchView.findViewById(id);
        imageView.setVisibility(View.GONE);
        imageView.setImageDrawable(null);
    }
}