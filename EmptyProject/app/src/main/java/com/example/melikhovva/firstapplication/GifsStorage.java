package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class GifsStorage {

    //TODO: rename file
    private static final String NAME_OF_SAVED_GIFS_FILE = "ids_and_names_of_saved_gifs";
    //TODO: rename key
    private static final String KEY = "SAVED_TRENDING_GIFS";

    private final GifLoader gifLoader;
    private final FileWriter fileWriter;
    private final GifsConverter gifsConverter;
    private final Directory directory;
    private final StringsPreferences stringsPreferences;

    public GifsStorage(final @NonNull GifLoader gifLoader,
                       final @NonNull FileWriter fileWriter,
                       final @NonNull GifsConverter gifsConverter,
                       final @NonNull Directory directory,
                       final @NonNull StringsPreferencesCreator stringsPreferencesCreator) {

        ValidatorNotNull.validateArguments(gifLoader, fileWriter, gifsConverter, directory, stringsPreferencesCreator);
        this.gifLoader = gifLoader;
        this.fileWriter = fileWriter;
        this.gifsConverter = gifsConverter;
        this.directory = directory;
        this.stringsPreferences = stringsPreferencesCreator.create(NAME_OF_SAVED_GIFS_FILE);

        makeFirstRecord();
    }

    private void makeFirstRecord() {

        if (!stringsPreferences.getString(KEY).isExists()) {
            writeGifs(new ArrayList<Gif>());
        }
    }


    public void doWithGifsIfExists(final @NonNull Optional.ActionWithContent<List<Gif>> actionWithGifs) {
        ValidatorNotNull.validateArguments(actionWithGifs);

        stringsPreferences.getString(KEY)
                .doWithContentIfExists(new Optional.ActionWithContent<String>() {
                    @Override
                    public void receive(final String string) {

                        gifsConverter.convertFromJSON(string)
                                    .doWithContentIfExists(new Optional.ActionWithContent<List<Gif>>() {
                                        @Override
                                        public void receive(final List<Gif> gifs) {

                                            actionWithGifs.receive(gifs);
                                        }
                                    });
                    }
                });
    }

    public void save(final @NonNull Gif gif) {
        ValidatorNotNull.validateArguments(gif);

        doWithGifsIfExists(new Optional.ActionWithContent<List<Gif>>() {
            @Override
            public void receive(final List<Gif> gifs) {

                if (!containsGifWithId(gifs, gif.getId())) {
                    new ActionStarter().startOnNewThread(new Runnable() {
                        @Override
                        public void run() {

                            gifLoader.load(gif)
                                    .doWithContentIfExists(new Optional.ActionWithContent<File>() {
                                        @Override
                                        public void receive(final File source) {

                                            final File destination = new File(directory.getAsFile(),
                                                                              gif.getId());
                                            if (fileWriter.copy(source, destination)) {

                                                gifs.add(new Gif(gif.getName(),
                                                                 destination.getAbsolutePath(),
                                                                 gif.getId()));
                                                writeGifs(gifs);
                                            }
                                        }
                                    });
                        }
                    });
                }
            }
        });
    }

    private void writeGifs(final List<Gif> gifs) {

        gifsConverter.convertToJSON(gifs)
                    .doWithContentIfExists(new Optional.ActionWithContent<String>() {
                        @Override
                        public void receive(final String string) {

                            stringsPreferences.putString(KEY, string);
                        }
                    });
    }

    public void doIfNotContains(final @NonNull Gif gif, final @NonNull Runnable action) {
        ValidatorNotNull.validateArguments(gif, action);

        doWithGifsIfExists(new Optional.ActionWithContent<List<Gif>>() {
            @Override
            public void receive(final List<Gif> gifs) {

                if (!containsGifWithId(gifs, gif.getId())) {
                    action.run();
                }
            }
        });
    }

    private boolean containsGifWithId(final List<Gif> gifs, final String id) {

        for (final Gif gif : gifs) {

            if (gif.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}