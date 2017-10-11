package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public final class FileComparator {

    private static final int BUFFER_SIZE = 1024;

    public boolean compare(final @NonNull File first, final @NonNull File second) {
        ValidatorNotNull.validateArguments(first, second);

        try {

            final InputStream firstIn = new FileInputStream(first);
            final InputStream secondIn = new FileInputStream(second);

            final byte[] firstBuffer = new byte[BUFFER_SIZE];
            final byte[] secondBuffer = new byte[BUFFER_SIZE];

            while ((firstIn.read(firstBuffer) > 0) && (secondIn.read(secondBuffer) > 0)) {
                if(!Arrays.equals(firstBuffer, secondBuffer)) {
                    firstIn.close();
                    secondIn.close();
                    return false;
                }
            }
            final boolean answer = (firstIn.read(firstBuffer) == secondIn.read(secondBuffer));
            firstIn.close();
            secondIn.close();
            return answer;

        } catch (final IOException e) {
            return false;
        }
    }
}
