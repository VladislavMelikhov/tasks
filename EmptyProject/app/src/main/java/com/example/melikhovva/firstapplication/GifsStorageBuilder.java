package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;

public final class GifsStorageBuilder {

    private final Context context;

    public GifsStorageBuilder(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);
        this.context = context;
    }

    public GifsStorage build() {
        return new GifsStorage(new GifLoaderFactory(context).newInstance(),
                               new FileWriter(),
                               new NameById(context.getSharedPreferences(context.getString(R.string.file_ids_and_names_of_saved_gifs),
                                                                         Context.MODE_PRIVATE)),
                               new InternalFilesDirectory(context));
    }
}
