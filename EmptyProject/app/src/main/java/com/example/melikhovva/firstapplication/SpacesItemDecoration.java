package com.example.melikhovva.firstapplication;


import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public final class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private final int columnCount;
    private final int spaceSize;

    public SpacesItemDecoration(final int columnCount, final int spaceSize) {
        validateArguments(columnCount, spaceSize);

        this.columnCount = columnCount;
        this.spaceSize = spaceSize;
    }

    private void validateArguments(final int columnsCount, final int spaceSize) {

        //TODO: duplication
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Column count should be at least 1");

        } else if (spaceSize < 0) {
            throw new IllegalArgumentException("Space size cannot be negative");
        }
    }

    @Override
    public void getItemOffsets(final @NonNull Rect outRect,
                               final @NonNull View view,
                               final @NonNull RecyclerView parent,
                               final @NonNull RecyclerView.State state) {

        ValidatorNotNull.validateArguments(outRect, view, parent, state);

        final int viewPosition = parent.getChildLayoutPosition(view);
        final int columnIndex = viewPosition % columnCount;
        final int rowIndex = viewPosition / columnCount;

        outRect.left = spaceSize * (columnCount - columnIndex) / columnCount;
        outRect.right = spaceSize * (columnIndex + 1) / columnCount;

        outRect.top = spaceSize;
    }
}
