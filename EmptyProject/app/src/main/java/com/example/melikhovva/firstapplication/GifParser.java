package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import org.json.*;
import java.util.ArrayList;
import java.util.List;

public final class GifParser {

    private static final String DATA_ARRAY = "data";
    private static final String SLUG = "slug";
    private static final String IMAGES = "images";
    private static final String ORIGINAL = "original";
    private static final String URL = "url";
    private static final String ID = "id";

    public Optional<List<Gif>> parseTrending(final @NonNull String source) {

        ValidatorNotNull.validateArguments(source);
        final List<Gif> gifs = new ArrayList<>();

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
                                         .getString(URL),

                                 currentGif
                                         .getString(ID)));
            }
            return new Existing<>(gifs);

        } catch (final JSONException e) {
            return new NonExistent<>();
        }
    }
}

