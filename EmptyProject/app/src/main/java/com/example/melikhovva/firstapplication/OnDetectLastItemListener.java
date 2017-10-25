package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class OnDetectLastItemListener extends RecyclerView.OnScrollListener {

    private static final String MY_TAG = "myLogs";

    private final GridLayoutManager gridLayoutManager;

    public OnDetectLastItemListener(final @NonNull GridLayoutManager gridLayoutManager) {
        ValidatorNotNull.validateArguments(gridLayoutManager);
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {

            final int totalItemCount = gridLayoutManager.getItemCount();
            Log.d(MY_TAG, String.valueOf(totalItemCount));

            final int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition() + 1;
            Log.d(MY_TAG, String.valueOf(lastVisibleItemPosition));

            if (lastVisibleItemPosition == totalItemCount) {

                Log.d(MY_TAG, "Last item");
                onDetectLastItem();
            }
        }
    }

    public abstract void onDetectLastItem();
}