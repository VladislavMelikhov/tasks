package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import org.json.*;
import java.util.ArrayList;
import java.util.Collection;

public final class GifParser {

    //TODO: replace enum with strings
    private enum JSONKeyStructure {

        data;

        enum Data {

            slug, images;

            enum Images {

                original;

                enum Original {

                    url;
                }
            }
        }
    }

    public Optional<Collection<Gif>> parseTrending(final @NonNull String source) {

        new ValidatorNotNull().validateArguments(source);
        final Collection<Gif> gifs = new ArrayList<>();

        try {
            final JSONArray data = new JSONObject(source)
                                        .getJSONArray(JSONKeyStructure.data.toString());

            for (int i = 0; i < data.length(); i++) {

                final JSONObject currentGif = data.getJSONObject(i);
                gifs.add(new Gif(currentGif
                                         .getString(JSONKeyStructure.Data.slug.toString()),

                                 currentGif
                                         .getJSONObject(JSONKeyStructure.Data.images.toString())
                                         .getJSONObject(JSONKeyStructure.Data.Images.original.toString())
                                         .getString(JSONKeyStructure.Data.Images.Original.url.toString())));
            }
            return new Existing<>(gifs);

        } catch (final JSONException e) {
            return new NonExistent<>();
        }
    }
}

