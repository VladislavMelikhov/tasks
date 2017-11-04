package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

public final class GifLoader {

    private final RequestManager requestManager;

    public GifLoader(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);
        requestManager = Glide.with(context);
    }

    public void loadAndDisplay(final @NonNull Gif gif, final @NonNull ImageView imageView) {
        ValidatorNotNull.validateArguments(gif, imageView);
        requestManager
                .load(gif.getUrl())
                .asGif()
                .skipMemoryCache(true)
                .into(imageView);
    }

    public void stopLoading(final @NonNull ImageView imageView) {
        ValidatorNotNull.validateArguments(imageView);
        Glide.clear(imageView);
    }

    public Optional<File> load(final @NonNull Gif gif) {
        ValidatorNotNull.validateArguments(gif);
        try {
            return new Existing<>(requestManager
                                          .load(gif.getUrl())
                                          .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                          .get());

        } catch (final InterruptedException e) {
            return new NonExistent<>();

        } catch (final ExecutionException e) {
            return new NonExistent<>();
        }
    }
}