package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;

public final class ApplicationToolsProvider {

    private final GifLoader gifLoader;
    private final GifsRequestor gifsRequestor;
    private final GifsStorage gifsStorage;

    public ApplicationToolsProvider(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);

        final GifsConverter gifsConverter = new GifsConverter();

        gifLoader = new GifLoader(context);
        gifsRequestor = new GifsRequestor(gifsConverter);
        gifsStorage = new GifsStorage(gifLoader,
                                      new FileWriter(),
                                      gifsConverter,
                                      new Directory(context.getFilesDir()),
                                      new StringsPreferencesCreator(context));
    }

    public GifLoader getGifLoader() {
        return gifLoader;
    }

    public GifsRequestor getGifsRequestor() {
        return gifsRequestor;
    }

    public GifsStorage getGifsStorage() {
        return gifsStorage;
    }
}