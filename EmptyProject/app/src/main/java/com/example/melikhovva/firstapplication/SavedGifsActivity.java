package com.example.melikhovva.firstapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SavedGifsActivity extends Activity {
    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_grid);

        final List<Gif> gifs = new ArrayList<>();

        readGifs(new AccessSharedPreferences().getIdsAndNamesOfSavedGifs(this),
                 gifs);

        new RecyclerViewConfigurator().setGifGrid((RecyclerView) findViewById(R.id.recycler_view),
                                                  gifs);
    }

    private void readGifs(final SharedPreferences nameById, final Collection<Gif> gifs) {

        for (final File file : getFilesDir().listFiles()) {

            final String gifId = file.getName();
            if (file.isFile() && nameById.contains(gifId)) {

                gifs.add(new Gif(nameById.getString(gifId, null),
                                 file.getAbsolutePath(),
                                 gifId));
            }
        }
    }
}
