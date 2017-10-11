package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public final class SquareRelativeLayout extends RelativeLayout {

    public SquareRelativeLayout(final Context context) {
        super(context);
    }

    public SquareRelativeLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}