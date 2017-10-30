package com.example.melikhovva.firstapplication;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public final class HttpRequest {

    private static final String HTTP_METHOD_GET = "GET";
    private static final int SUCCESSFUL_RESPONSE_CODE_LOWER_BOUND = 200;
    private static final int SUCCESSFUL_RESPONSE_CODE_UPPER_BOUND = 299;

    public HttpRequest(final @NonNull String url, final @NonNull HttpRequestListener httpRequestListener) {
        ValidatorNotNull.validateArguments(url, httpRequestListener);

        new ActionStarter().startOnNewThread(new Runnable() {
            @Override
            public void run() {
                final HttpResponse response = doInBackground(url);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        httpRequestListener.interactionFinished(response);
                    }
                });
            }
        });
    }

    private HttpResponse doInBackground(final String url) {
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(HTTP_METHOD_GET);

            try {
                connection.connect();

                final int responseCode = connection.getResponseCode();
                if (SUCCESSFUL_RESPONSE_CODE_LOWER_BOUND <= responseCode &&
                    responseCode <= SUCCESSFUL_RESPONSE_CODE_UPPER_BOUND) {
                    return readAllText(connection.getInputStream());

                } else {
                    return failedHttpResponse();
                }
            } finally {
                connection.disconnect();
            }
        } catch (final IOException e) {
            return failedHttpResponse();
        }
    }

    private HttpResponse failedHttpResponse() {
        return new HttpResponse(ResponseStatus.Failed,
                                new NonExistent<String>());
    }

    private HttpResponse readAllText(final InputStream inputStream) {
        final Scanner scanner = new Scanner(inputStream)
                                    .useDelimiter("\\A");
        if (scanner.hasNext()) {
            return new HttpResponse(ResponseStatus.Successful,
                                    new Existing<>(scanner.next()));

        } else {
            return new HttpResponse(ResponseStatus.Successful,
                                   new NonExistent<String>());
        }
    }
}
