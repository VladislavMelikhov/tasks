package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.File;

//TODO: rename class
public interface GifLoader {

    void loadAndDisplay(@NonNull Gif gif, @NonNull ImageView imageView);

    Optional<File> load(@NonNull Gif gif);
}