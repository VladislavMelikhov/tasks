package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;

public final class InstancesHolder {

    //TODO: rename file
    private static final String NAME_OF_SAVED_GIFS_FILE = "ids_and_names_of_saved_gifs";

    private static boolean initialized = false;
    private static GifLoader gifLoader;
    private static GifsRequestor gifsRequestor;
    private static GifsConverter gifsConverter;
    private static GifsStorage gifsStorage;

    private InstancesHolder() {}

    public static void init(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);

        if (!initialized) {
            gifLoader = new GifLoader(context);
            gifsRequestor = new GifsRequestor();
            gifsConverter = new GifsConverter();
            gifsStorage = new GifsStorage(getGifLoader(),
                                          new FileWriter(),
                                          getGifsConverter(),
                                          new Directory(context.getFilesDir()),
                                          new StringByKey(context.getSharedPreferences(NAME_OF_SAVED_GIFS_FILE,
                                                                                       Context.MODE_PRIVATE)));
            initialized = true;

        } else {
            throw new IllegalStateException("InstancesHolder has already been initialized");
        }
    }

    public static GifLoader getGifLoader() {
        validateInitialize(gifLoader);
        return gifLoader;
    }

    public static GifsRequestor getGifsRequestor() {
        validateInitialize(gifsRequestor);
        return gifsRequestor;
    }

    public static GifsConverter getGifsConverter() {
        validateInitialize(gifsConverter);
        return gifsConverter;
    }

    public static GifsStorage getGifsStorage() {
        validateInitialize(gifsStorage);
        return gifsStorage;
    }

    private static void validateInitialize(final Object object) {
        if (object == null) {
            throw new IllegalStateException("Instance has not been created");
        }
    }
}