package com.example.melikhovva.firstapplication;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

//TODO: rename
public final class Connection {
    private static final String MY_TAG = "myLogs";
    private static final String HTTP_METHOD_GET = "GET";

    //TODO: rename
    public static void request(final @NonNull String urlName) {

        startActionOnNewThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL url = new URL(urlName);
                    final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod(HTTP_METHOD_GET);

                    try {
                        connection.connect();

                        //TODO: magic numbers
                        if (connection.getResponseCode() / 100 == 2) {

                            final Scanner scanner = new Scanner(connection.getInputStream());

                            while (scanner.hasNextLine()) {
                                Log.d(MY_TAG, scanner.nextLine());
                            }
                        } else {
                            Log.d(MY_TAG, String.valueOf(connection.getResponseCode()) + " " + connection.getResponseMessage());
                        }
                    } finally {
                        connection.disconnect();
                    }
                } catch (final IOException e) {
                    Log.e(MY_TAG, e.getMessage());
                }
            }
        });
    }
    private static void startActionOnNewThread(final Runnable action) {
        new Thread(action).start();
    }
}
