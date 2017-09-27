package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public final class HttpResponse {

    private final ResponseStatus responseStatus;
    private final OptionalResponseBody responseBody;

    public HttpResponse(final @NonNull ResponseStatus responseStatus,
                        final @NonNull OptionalResponseBody responseBody) {
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public OptionalResponseBody getResponseBody() {
        return responseBody;
    }
}
