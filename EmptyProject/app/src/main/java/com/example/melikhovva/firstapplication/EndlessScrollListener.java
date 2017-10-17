package com.example.melikhovva.firstapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private boolean loading;
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

            if (!loading && (gridLayoutManager.findLastVisibleItemPosition() + 1 + visibleThreshold >= gridLayoutManager.getItemCount())) {
                loadingStarted();
                loadMore();
            }
        }
    }

    public abstract void loadMore();
}