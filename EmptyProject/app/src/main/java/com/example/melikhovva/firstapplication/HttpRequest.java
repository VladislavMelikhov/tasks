package com.example.melikhovva.firstapplication;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public final class HttpRequest extends AsyncTask<String, Void, HttpResponse> {

    private static final String HTTP_METHOD_GET = "GET";

    private final HttpRequestListener httpRequestListener;

    public HttpRequest(final HttpRequestListener httpRequestListener) {
        this.httpRequestListener = httpRequestListener;
    }

    @Override
    protected HttpResponse doInBackground(final @NonNull String... params) {
        new ValidatorNotNull().validateArguments(params);

        if (params.length > 0) {
            try {
                final URL url = new URL(params[0]);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(HTTP_METHOD_GET);

                try {
                    connection.connect();

                    //TODO: response code
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
        } else {
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
            return new HttpResponse(ResponseStatus.Successfull,
                                    new Existing<>(scanner.next()));

        } else {
            return new HttpResponse(ResponseStatus.Successfull,
                                   new NonExistent<String>());
        }
    }

    @Override
    protected void onPostExecute(final HttpResponse response) {
        httpRequestListener.interactionFinished(response);
    }
}
