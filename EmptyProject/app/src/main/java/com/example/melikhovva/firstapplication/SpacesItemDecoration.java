package com.example.melikhovva.firstapplication;


import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public final class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private final int columnCount,
                      rowCount,
                      spaceSize;

    public SpacesItemDecoration(final int gifCount, final int columnCount, final int spaceSize) {
        validateArguments(gifCount, columnCount, spaceSize);

        this.columnCount = columnCount;
        this.rowCount = (int) Math.ceil((double)gifCount / columnCount);
        this.spaceSize = spaceSize;
    }

    private void validateArguments(final int gifCount, final int columnsCount, final int spaceSize) {

        if (gifCount <= 0) {
            throw new IllegalArgumentException("Gif count should be at least 1");

        } else if (columnsCount <= 0) {
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

        outRect.top = spaceSize * (rowCount - rowIndex) / rowCount;
        outRect.bottom = spaceSize * (rowIndex + 1) / rowCount;
    }
}
