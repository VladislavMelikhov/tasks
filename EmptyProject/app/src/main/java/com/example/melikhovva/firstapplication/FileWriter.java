package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class FileWriter {

    private static final int BUFFER_SIZE = 1024;

    public boolean copy(final @NonNull File source, final @NonNull File destination) {
        ValidatorNotNull.validateArguments(destination, source);

        try {
            final InputStream in = new FileInputStream(source);
            final OutputStream out = new FileOutputStream(destination);

            final byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            //TODO: close streams
            in.close();
            out.close();
            return true;

        } catch (final IOException e) {
            return false;
        }
    }
}
