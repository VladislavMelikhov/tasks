package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import org.json.*;
import java.util.ArrayList;
import java.util.List;

public final class GifsConverter {

    private static GifsConverter gifConverter;

    public static void init() {

        if (gifConverter == null) {
            gifConverter = new GifsConverter();

        } else {
            throw new IllegalStateException("GifsConverter has already been initialized");
        }
    }

    public static GifsConverter getInstance() {

        if (gifConverter == null) {
            throw new IllegalStateException("GifsConverter has not been initialized");

        } else {
            return gifConverter;
        }
    }

    private GifsConverter() {

    }

    private static final String DATA_ARRAY = "data";
    private static final String SLUG = "slug";
    private static final String IMAGES = "images";
    private static final String ORIGINAL = "original";
    private static final String URL = "url";
    private static final String ID = "id";

    public Optional<List<Gif>> convertFromJSON(final @NonNull String source) {

        ValidatorNotNull.validateArguments(source);
        final List<Gif> gifs = new ArrayList<>();

        try {
            final JSONArray data = new JSONObject(source)
                                        .getJSONArray(DATA_ARRAY);

            for (int i = 0; i < data.length(); i++) {

                final JSONObject currentGif = data.getJSONObject(i);
                gifs.add(new Gif(currentGif.getString(SLUG),

                                 currentGif.getJSONObject(IMAGES)
                                         .getJSONObject(ORIGINAL)
                                         .getString(URL),

                                 currentGif.getString(ID)));
            }
            return new Existing<>(gifs);

        } catch (final JSONException e) {
            return new NonExistent<>();
        }
    }

     public Optional<String> convertToJSON(final @NonNull List<Gif> gifs) {
         ValidatorNotNull.validateArguments(gifs);
         ValidatorNotNull.validateArguments(gifs.toArray());

         final JSONArray jsonArray = new JSONArray();

         try {

             for (final Gif gif : gifs) {

                 final JSONObject currentGif = new JSONObject();
                 currentGif.put(SLUG, gif.getName());

                 currentGif.put(IMAGES, new JSONObject()
                         .put(ORIGINAL, new JSONObject()
                         .put(URL, gif.getUrl())));

                 currentGif.put(ID, gif.getId());
                 jsonArray.put(currentGif);
             }
             return new Existing<>(new JSONObject().put(DATA_ARRAY, jsonArray)
                                                    .toString());

         } catch (final JSONException e) {
             return new NonExistent<>();
         }
     }
}