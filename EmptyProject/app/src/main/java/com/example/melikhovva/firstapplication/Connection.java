package com.example.melikhovva.firstapplication;


import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public final class Connection {
    private static final String MY_TAG = "myLogs";

    public static void request(final @Nullable String urlName){

        if(urlName != null) {

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final URL url = new URL(urlName);
                        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        try {
                            connection.connect();

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
            thread.start();
        } else {
            Log.e(MY_TAG, "urlName is null");
        }
    }
}
