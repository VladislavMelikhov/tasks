package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public final class HttpResponse {

    private final ResponseStatus responseStatus;
    private final Optional<String> responseBody;

    public HttpResponse(final @NonNull ResponseStatus responseStatus,
                        final @NonNull Optional<String> responseBody) {

        new ValidatorNotNull().validateArguments(responseStatus, responseBody);
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public Optional<String> getResponseBody() {
        return responseBody;
    }
}
