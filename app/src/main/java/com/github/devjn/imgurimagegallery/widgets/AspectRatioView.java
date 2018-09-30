package com.github.devjn.imgurimagegallery.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.devjn.imgurimagegallery.R;

/**
 * Extension of FrameLayout that assumes a measured (non-zero) width and sets the
 * height according to the provided aspect ratio.
 */
public class AspectRatioView extends FrameLayout {
    private float aspectRatio = 0f;

    public AspectRatioView(Context context) {
        this(context, null, 0);
    }

    public AspectRatioView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public AspectRatioView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.AspectRatioView, defStyle, 0);

        aspectRatio = a.getFloat(R.styleable.AspectRatioView_aspectRatio, 0);

        if (aspectRatio == 0f) {
            throw new IllegalArgumentException("You must specify an aspect ratio when using the " +
                    "AspectRatioView.");
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int width, height;
        if (aspectRatio != 0) {
            width = widthSize;
            height = (int) (width / aspectRatio);
            int exactWidthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int exactHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            super.onMeasure(exactWidthSpec, exactHeightSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
}
