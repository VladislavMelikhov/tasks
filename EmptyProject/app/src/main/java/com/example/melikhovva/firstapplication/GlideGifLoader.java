package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public final class GlideGifLoader implements GifLoader {

    private final Context context;

    public GlideGifLoader(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);
        this.context = context;
    }

    @Override
    public void loadAndDisplay(final @NonNull Gif gif, final @NonNull ImageView imageView) {
        ValidatorNotNull.validateArguments(gif, imageView);
        Glide.with(context)
                .load(gif.getUrl())
                .asGif()
                .into(imageView);
    }

    @Override
    public Optional<File> load(final @NonNull Gif gif) {
        ValidatorNotNull.validateArguments(gif);
        try {
            return new Existing<>(Glide.with(context)
                                          .load(gif.getUrl())
                                          .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                          .get());

        } catch (final Exception e) {
            return new NonExistent<>();
        }
    }
}