package com.example.melikhovva.firstapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

public final class DetailGifActivity extends AppCompatActivity {

    public static final String DETAIL_GIF = "DETAIL_GIF";

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gif);

        final Gif gif = (Gif) getIntent().getSerializableExtra(DETAIL_GIF);

        configureToolBar(gif.getName());
        findViewById(R.id.square_relative_layout).setBackgroundColor(Color.BLUE);
        configureSaveButton(gif);
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

    private void configureSaveButton(final Gif gif) {
        final View saveButton = findViewById(R.id.save_button);

        final GifsStorage gifsStorage = new GifsStorageBuilder(this).build();

        final boolean contains = gifsStorage.contains(gif);
        saveButton.setEnabled(!contains);

        if (!contains) {

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    startActionOnNewThread(new Runnable() {
                        @Override
                        public void run() {
                            gifsStorage.save(gif);
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
                view.getContext().startActivity(new Intent(view.getContext(),
                                                           SavedGifsActivity.class));
            }
        });
    }

    private void displayGif(final Gif gif) {
        new GifLoaderFactory(this).newInstance()
                                  .loadAndDisplay(gif,
                                                  (ImageView) findViewById(R.id.image_view));
    }
}