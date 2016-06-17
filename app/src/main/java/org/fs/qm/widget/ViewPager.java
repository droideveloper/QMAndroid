package org.fs.qm.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.widget.ViewPager
 */
public class ViewPager extends android.support.v4.view.ViewPager {

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * fooling to force width based square
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //first fool the king
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        //then betray GOOGLE
    }

    /**
     * caused crash if we not implement this with draw order return index as i
     * @param childCount
     * @param i
     * @return
     */
    @Override protected int getChildDrawingOrder(int childCount, int i) {
        return i;
    }
}
