package com.example.melikhovva.firstapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public final class DetailGifActivity extends AppCompatActivity {

    public static final String DETAIL_GIF = "DETAIL_GIF";
    public static final String MY_TAG = "MyLogs";

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gif);

        final Gif gif = (Gif) getIntent().getSerializableExtra(DETAIL_GIF);

        configureToolBar(gif.getName());
        findViewById(R.id.square_relative_layout).setBackgroundColor(Color.BLUE);

        configureSaveButton(new AccessSharedPreferences().getIdsAndNamesOfSavedGifs(this),
                            gif);

        configureSavedGifsButton();
        displayGif(gif);
    }

    private void configureToolBar(final String title) {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onBackPressed();
            }
        });
    }

    private void configureSaveButton(final SharedPreferences nameById, final Gif gif) {
        final View saveButton = findViewById(R.id.save_button);

        final String gifId = gif.getId();
        final boolean containsId = nameById.contains(gifId);
        saveButton.setEnabled(!containsId);

        if (!containsId) {

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    startActionOnNewThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!nameById.contains(gifId)) {

                                try {

                                    final File source = Glide.with(DetailGifActivity.this)
                                                            .load(gif.getUrl())
                                                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                            .get();

                                    final File destination = new File(getFilesDir(), gifId);

                                    if (new FileWriter().copy(source, destination)) {

                                        if (new FileComparator().compare(source, destination)) {
                                            nameById.edit()
                                                    .putString(gifId, gif.getName())
                                                    .apply();

                                            log("File has written successfully");

                                        } else {
                                            log("Written file is incorrect");
                                        }
                                    } else {
                                        log("Failed by writing to file");
                                    }
                                } catch (final Exception e) {
                                    log("Source file is incorrect");
                                }
                            } else {
                                log("File has already been written");
                            }
                        }
                    });
                }
            });
        }
    }

    private void startActionOnNewThread(final Runnable action) {
        new Thread(action).start();
    }

    private void log(final String message) {
        Log.d(MY_TAG, message);
    }

    private void configureSavedGifsButton() {
        findViewById(R.id.view_saved_gifs_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.getContext().startActivity(new Intent(view.getContext(),
                                                           SavedGifsActivity.class));
            }
        });
    }

    private void displayGif(final Gif gif) {
        Glide.with(this)
                .load(gif.getUrl())
                .asGif()
                .into((ImageView) findViewById(R.id.image_view));
    }
}