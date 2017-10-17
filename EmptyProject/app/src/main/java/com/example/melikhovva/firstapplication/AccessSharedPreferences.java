package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.content.SharedPreferences;

public final class AccessSharedPreferences {

    public SharedPreferences getIdsAndNamesOfSavedGifs(final Context context) {
        return context.getSharedPreferences(context.getString(R.string.file_ids_and_names_of_saved_gifs), Context.MODE_PRIVATE);
    }
}
