package org.fs.qm.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by Fatih on 15/06/16.
 * as org.fs.qm.widget.FocuslessNestedScrollView
 */
public class FocuslessNestedScrollView extends NestedScrollView {

    public FocuslessNestedScrollView(Context context) {
        super(context);
    }

    public FocuslessNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocuslessNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getDescendantFocusability() {
        return FOCUS_BLOCK_DESCENDANTS;//I gone like this scrollView
    }
}
