package com.example.melikhovva.firstapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public final class Gif implements Parcelable {

    private final String name;
    private final String url;

    public Gif(final @NonNull String name, final @NonNull String url) {
        ValidatorNotNull.validateArguments(name, url);
        this.name = name;
        this.url = url;
    }

    private Gif(final Parcel parcel) {
        name = parcel.readString();
        url = parcel.readString();
    }

    public static final Creator<Gif> CREATOR = new Creator<Gif>() {
        @Override
        public Gif createFromParcel(final Parcel source) {
            return new Gif(source);
        }

        @Override
        public Gif[] newArray(final int size) {
            return new Gif[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel destination, final int flags) {
        destination.writeString(name);
        destination.writeString(url);
    }
}