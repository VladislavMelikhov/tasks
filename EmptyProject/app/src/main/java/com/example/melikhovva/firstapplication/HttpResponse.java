package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

public final class HttpResponse {

    private final ResponseStatus responseStatus;
    private final OptionalContent<String> responseBody;

    //Add validation!
    public HttpResponse(final @NonNull ResponseStatus responseStatus,
                        final @NonNull OptionalContent<String> responseBody) {

        new ValidatorNotNull().argumentsValidation(responseStatus, responseBody);
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public OptionalContent<String> getResponseBody() {
        return responseBody;
    }
}
