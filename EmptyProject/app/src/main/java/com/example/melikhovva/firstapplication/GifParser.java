package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import org.json.*;
import java.util.ArrayList;
import java.util.Collection;

public final class GifParser {

    private static final String DATA_ARRAY = "data",
                                SLUG = "slug",
                                IMAGES = "images",
                                ORIGINAL = "original",
                                URL = "url";

    public Optional<Collection<Gif>> parseTrending(final @NonNull String source) {

        new ValidatorNotNull().validateArguments(source);
        final Collection<Gif> gifs = new ArrayList<>();

        try {
            final JSONArray data = new JSONObject(source)
                                        .getJSONArray(DATA_ARRAY);

            for (int i = 0; i < data.length(); i++) {

                final JSONObject currentGif = data.getJSONObject(i);
                gifs.add(new Gif(currentGif
                                         .getString(SLUG),

                                 currentGif
                                         .getJSONObject(IMAGES)
                                         .getJSONObject(ORIGINAL)
                                         .getString(URL)));
            }
            return new Existing<>(gifs);

        } catch (final JSONException e) {
            return new NonExistent<>();
        }
    }
}

