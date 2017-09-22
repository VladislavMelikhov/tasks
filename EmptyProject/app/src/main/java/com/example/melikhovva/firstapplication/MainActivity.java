package com.example.melikhovva.firstapplication;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.Arrays;

public final class MainActivity extends Activity {

    private static final int DATA_SIZE = 40;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] data = new String[DATA_SIZE];
        Arrays.fill(data, "");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StringsAdapter(data));

        Connection.request("http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC");
    }
}