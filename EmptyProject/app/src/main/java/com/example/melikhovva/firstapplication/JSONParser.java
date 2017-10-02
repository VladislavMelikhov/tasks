package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import org.json.*;
import java.util.ArrayList;

public final class JSONParser {

    private enum Root {

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

    //Nullable or NonNull?
    public OptionalGifs parseTrendGifs(final @NonNull String source) {

        if (source == null) {
            return new NotExistGifs();
        } else {

            final ArrayList<Gif> gifs = new ArrayList<>();

            try {
                final JSONObject root = new JSONObject(source);
                final JSONArray data = root.getJSONArray(Root.data.toString());

                for(int i = 0; i < data.length(); i++) {

                    gifs.add(new Gif(    data.getJSONObject(i)
                                             .getString(Root.Data.slug.toString()),

                                         data.getJSONObject(i)
                                             .getJSONObject(Root.Data.images.toString())
                                             .getJSONObject(Root.Data.Images.original.toString())
                                             .getString(Root.Data.Images.Original.url.toString())));
                }
                return new ExistGifs(gifs);

            } catch(final JSONException e) {
                return new NotExistGifs();
            }
        }
    }
}

