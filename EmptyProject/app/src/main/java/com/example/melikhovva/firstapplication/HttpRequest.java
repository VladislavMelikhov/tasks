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

    private HttpResponse failedHttpResponse() {
        return new HttpResponse(ResponseStatus.Failed,
                                new NotExistResponseBody());
    }

    private Scanner readAllTextScanner(final InputStream inputStream) {
        return new Scanner(inputStream)
                    .useDelimiter("\\A");

    }

    @Override
    protected HttpResponse doInBackground(final @NonNull String... params) {

        if (params.length > 0) {

            try {
                final URL url = new URL(params[0]);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(HTTP_METHOD_GET);

                try {
                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        final Scanner scanner = readAllTextScanner(connection.getInputStream());
                        if (scanner.hasNext()) {
                            return new HttpResponse(ResponseStatus.Successfull,
                                                    new ExistResponseBody(scanner.next()));
                        } else {
                            return new HttpResponse(ResponseStatus.Successfull,
                                                    new NotExistResponseBody());
                        }
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

    @Override
    protected void onPostExecute(final HttpResponse response) {
        httpRequestListener.interactionFinished(response);
    }
}
