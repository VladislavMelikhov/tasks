package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;

import java.util.List;

public final class GifsRequestor {

    public void requestTrending(final int offset, final int limit, final @NonNull Optional.ActionWithContent<List<Gif>> actionWithGifs) {
        ValidatorNotNull.validateArguments(actionWithGifs);

        if (validateOffsetAndLimit(offset, limit)) {

            new HttpRequest().execute("http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC" + "&limit=" + limit + "&offset=" + offset,
                                      new HttpRequestListener() {
                                          @Override
                                          public void interactionFinished(final HttpResponse response) {

                                              if (response.getResponseStatus() == ResponseStatus.Successful) {

                                                  response.getResponseBody()
                                                          .doWithContentIfExists(new Optional.ActionWithContent<String>() {
                                                              @Override
                                                              public void receive(final String body) {

                                                                  new GifsConverter().convertFromJSON(body)
                                                                                    .doWithContentIfExists(actionWithGifs);
                                                              }
                                                          });
                                              }
                                          }
                                      });
        }
    }

    private boolean validateOffsetAndLimit(final int offset, final int limit) {
        return (offset >= 0) && (limit >= 0);
    }
}