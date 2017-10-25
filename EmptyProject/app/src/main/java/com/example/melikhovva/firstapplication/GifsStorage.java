package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class GifsStorage {

    private static GifsStorage gifsStorage;

    public static void init(final @NonNull Context context) {
        ValidatorNotNull.validateArguments(context);

        if (gifsStorage == null) {
            gifsStorage = new GifsStorage(new GifLoader(context),
                                          new FileWriter(),
                                          new GifsConverter(),
                                          new Directory(context.getFilesDir()),
                                          new StringByKey(context.getSharedPreferences(context.getString(R.string.file_ids_and_names_of_saved_gifs),
                                                                                       Context.MODE_PRIVATE)));
        } else {
            throw new IllegalStateException("GifsStorage has already been initialized");
        }
    }

    public static GifsStorage getInstance() {

        if (gifsStorage == null) {
            throw new IllegalStateException("GifsStorage has not been initialized");

        } else {
            return gifsStorage;
        }
    }

    private static final String KEY = "SAVED_TRENDING_GIFS";

    private final GifLoader gifLoader;
    private final FileWriter fileWriter;
    private final GifsConverter gifsConverter;
    private final Directory directory;
    private final StringByKey stringByKey;

    private GifsStorage(final @NonNull GifLoader gifLoader,
                        final @NonNull FileWriter fileWriter,
                        final @NonNull GifsConverter gifsConverter,
                        final @NonNull Directory directory,
                        final @NonNull StringByKey stringByKey) {

        ValidatorNotNull.validateArguments(gifLoader, fileWriter, gifsConverter, directory, stringByKey);
        this.gifLoader = gifLoader;
        this.fileWriter = fileWriter;
        this.gifsConverter = gifsConverter;
        this.directory = directory;
        this.stringByKey = stringByKey;

        makeFirstRecord();
    }

    private void makeFirstRecord() {

        if (!stringByKey.getString(KEY).isExists()) {
            writeGifs(new ArrayList<Gif>());
        }
    }


    public void doWithGifsIfExists(final @NonNull Optional.ActionWithContent<List<Gif>> actionWithGifs) {
        ValidatorNotNull.validateArguments(actionWithGifs);

        stringByKey.getString(KEY)
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

                if (!gifs.contains(gif)) {
                    new ThreadCreator().startActionOnNewThread(new Runnable() {
                        @Override
                        public void run() {

                            gifLoader.load(gif)
                                    .doWithContentIfExists(new Optional.ActionWithContent<File>() {
                                        @Override
                                        public void receive(final File source) {

                                            final File destination = new File(directory.getDirectory(),
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

                            stringByKey.putString(KEY, string);
                        }
                    });
    }

    public void doIfNotContains(final @NonNull Gif gif, final @NonNull Runnable action) {
        ValidatorNotNull.validateArguments(gif, action);

        doWithGifsIfExists(new Optional.ActionWithContent<List<Gif>>() {
            @Override
            public void receive(final List<Gif> gifs) {

                if (!gifs.contains(gif)) {
                    action.run();
                }
            }
        });
    }
}