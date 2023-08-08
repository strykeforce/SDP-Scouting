package org.wildstang.wildrank.androidv2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * An implementation of RelativeLayout that constrains itself to a 16:9 aspect ratio.
 */
public class SixteenNineAspectRatioLayout extends RelativeLayout {

    public SixteenNineAspectRatioLayout(Context context) {
        super(context, null);
    }

    public SixteenNineAspectRatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SixteenNineAspectRatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) ((width * 9f) / 16f);

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);

        getLayoutParams().height = height;
        getLayoutParams().width = width;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
