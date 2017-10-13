package com.example.melikhovva.firstapplication;

import android.content.Context;
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

        final SharedPreferences mappingIdToName = getSharedPreferences(getString(R.string.file_ids_and_names_of_saved_gifs),
                                                                       Context.MODE_PRIVATE);

        configureToolBar(gif.getName());
        findViewById(R.id.square_relative_layout).setBackgroundColor(Color.BLUE);
        configureSaveButton(mappingIdToName, gif);
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

    private void configureSaveButton(final SharedPreferences mappingIdToName, final Gif gif) {
        final View saveButton = findViewById(R.id.save_button);

        if (mappingIdToName.contains(gif.getId())) {
            saveButton.setEnabled(false);

        } else {
            saveButton.setEnabled(true);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    startActionOnNewThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!mappingIdToName.contains(gif.getId())) {

                                try {

                                    final File source = Glide.with(DetailGifActivity.this)
                                                            .load(gif.getUrl())
                                                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                            .get();

                                    final File destination = new File(getFilesDir(), gif.getId());

                                    if (new FileWriter().copy(source, destination)) {

                                        if (new FileComparator().compare(source, destination)) {
                                            mappingIdToName.edit()
                                                            .putString(gif.getId(), gif.getName())
                                                            .apply();

                                            Log.d(MY_TAG, "File has written successfully");

                                        } else {
                                            Log.d(MY_TAG, "Written file is incorrect");
                                        }
                                    } else {
                                        Log.d(MY_TAG, "Failed by writing to file");
                                    }
                                } catch (final Exception e) {
                                    Log.d(MY_TAG, "Source file is incorrect");
                                }
                            } else {
                                Log.d(MY_TAG, "File has already been written");
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

    private void configureSavedGifsButton() {
        findViewById(R.id.view_saved_gifs_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(view.getContext(), SavedGifsActivity.class);
                view.getContext().startActivity(intent);
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