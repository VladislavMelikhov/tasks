package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class OnDetectLastItemListener extends RecyclerView.OnScrollListener {

    private final GridLayoutManager gridLayoutManager;

    public OnDetectLastItemListener(final @NonNull GridLayoutManager gridLayoutManager) {
        ValidatorNotNull.validateArguments(gridLayoutManager);
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public final void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {

            final int totalItemCount = gridLayoutManager.getItemCount();
            final int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition() + 1;

            if (lastVisibleItemPosition == totalItemCount) {
                onDetectLastItem();
            }
        }
    }

    public abstract void onDetectLastItem();
}