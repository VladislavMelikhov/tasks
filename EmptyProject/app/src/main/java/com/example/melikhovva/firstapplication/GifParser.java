package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import org.json.*;
import java.util.ArrayList;
import java.util.Collection;

public final class GifParser {

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

    public OptionalContent<Collection<Gif>> parseTrending(final @NonNull String source) {

        new ValidatorNotNull().argumentsValidation(source);
        final Collection<Gif> gifs = new ArrayList<>();

        try {
            final JSONArray data = new JSONObject(source)
                                        .getJSONArray(JSONKeyStructure.data.toString());

            for (int i = 0; i < data.length(); i++) {

                final JSONObject currentJSONObject = data.getJSONObject(i);
                gifs.add(new Gif(currentJSONObject
                                         .getString(JSONKeyStructure.Data.slug.toString()),

                                 currentJSONObject
                                         .getJSONObject(JSONKeyStructure.Data.images.toString())
                                         .getJSONObject(JSONKeyStructure.Data.Images.original.toString())
                                         .getString(JSONKeyStructure.Data.Images.Original.url.toString())));
            }
            return new ExistContent<>(gifs);

        } catch (final JSONException e) {
            return new NotExistContent<>();
        }
    }
}

