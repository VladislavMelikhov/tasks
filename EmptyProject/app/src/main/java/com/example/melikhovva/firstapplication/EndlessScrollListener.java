package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    public boolean loading;
    private final GridLayoutManager gridLayoutManager;

    public EndlessScrollListener(final @NonNull GridLayoutManager gridLayoutManager) {
        ValidatorNotNull.validateArguments(gridLayoutManager);
        this.gridLayoutManager = gridLayoutManager;
        loading = false;
    }

    private void loadingStarted() {
        loading = true;
    }

    protected void loadingFinished() {
        loading = false;
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 0) {

            final int visibleThreshold = recyclerView.getContext().getResources().getInteger(R.integer.column_count);

            final int totalItemCount = gridLayoutManager.getItemCount();
            final int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition() + 1;

            if (!loading && (lastVisibleItemPosition + visibleThreshold >= totalItemCount)) {
                loadingStarted();
                loadMore();
            }
        }
    }

    public abstract void loadMore();
}