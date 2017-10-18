package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class GifsStorage {

    private final GifLoader gifLoader;
    private final FileWriter fileWriter;
    private final NameById nameById;
    private final Directory directory;

    public GifsStorage(final @NonNull GifLoader gifLoader,
                       final @NonNull FileWriter fileWriter,
                       final @NonNull NameById nameById,
                       final @NonNull Directory directory) {

        ValidatorNotNull.validateArguments(gifLoader, fileWriter, nameById, directory);
        this.gifLoader = gifLoader;
        this.fileWriter = fileWriter;
        this.nameById = nameById;
        this.directory = directory;
    }

    public void save(final @NonNull Gif gif) {
        ValidatorNotNull.validateArguments(gif);

        final String gifId = gif.getId();
        if (!nameById.contains(gifId)) {

            gifLoader.load(gif).doWithContentIfExists(new Optional.ActionWithContent<File>() {
                @Override
                public void receive(final File source) {

                    final File destination = new File(directory.getDirectory(),
                                                      gifId);

                    if (fileWriter.copy(source, destination)) {
                        nameById.putGifName(gif);
                    }
                }
            });
        }
    }

    public boolean contains(final @NonNull Gif gif) {
        ValidatorNotNull.validateArguments(gif);
        return nameById.contains(gif.getId());
    }

    public List<Gif> getGifs() {

        final List<Gif> gifs = new ArrayList<>();

        for (final File file : directory.getDirectory().listFiles()) {

            final String gifId = file.getName();
            if (file.isFile() && nameById.contains(gifId)) {

                gifs.add(new Gif(nameById.getName(gifId, null),
                                 file.getAbsolutePath(),
                                 gifId));
            }
        }
        return gifs;
    }
}