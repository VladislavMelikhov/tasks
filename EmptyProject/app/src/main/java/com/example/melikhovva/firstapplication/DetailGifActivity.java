package com.example.melikhovva.firstapplication;

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
        initializeToolBar(gif.getName());

        findViewById(R.id.square_relative_layout).setBackgroundColor(Color.BLUE);

        final ImageView imageView = (ImageView) findViewById(R.id.image_view);
        Glide.with(this)
                .load(gif.getUrl())
                .asGif()
                .into(imageView);

        //TODO: code structure!
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                startActionOnNewThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            final File source = Glide.with(imageView.getContext())
                                                        .load(gif.getUrl())
                                                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                        .get();

                            final File target = new File(getExternalFilesDir(null), gif.getName() + ".gif");

                            if (new FileWriter().writeToFileReadFrom(target, source)) {

                                //TODO: What to do if app is closed
                                if (new FileComparator().compare(target, source)) {
                                    Log.d(MY_TAG, "File has written successfully");
                                } else {
                                    Log.e(MY_TAG, "Written fail is incorrect");
                                }
                            } else {
                                Log.e(MY_TAG, "Failed by writing to file");
                            }
                        } catch (final Exception e) {
                            Log.e(MY_TAG, e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void initializeToolBar(final String title) {
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

    private void startActionOnNewThread(final Runnable action) {
        new Thread(action).start();
    }
}