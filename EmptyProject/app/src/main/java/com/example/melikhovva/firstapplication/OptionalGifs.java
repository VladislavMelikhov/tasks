package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import java.util.Collection;

public interface OptionalGifs {

    interface ActionWithGifs {
        void receive(Collection<Gif> gifs);
    }

    boolean isExists();

    void doWithGifsIfExists(ActionWithGifs actionWithGifs);
}

final class ExistGifs implements OptionalGifs {

    private final Collection<Gif> gifs;

    public ExistGifs(final @NonNull Collection<Gif> gifs) {
        if(gifs == null) {
            throw new IllegalArgumentException("No value.");
        }
        this.gifs = gifs;
    }

    @Override
    public boolean isExists() {
        return true;
    }

    @Override
    public void doWithGifsIfExists(final ActionWithGifs actionWithGifs) {
        actionWithGifs.receive(gifs);
    }
}

final class NotExistGifs implements OptionalGifs {

    @Override
    public boolean isExists() {
        return false;
    }

    @Override
    public void doWithGifsIfExists(final ActionWithGifs actionWithGifs) {

    }
}