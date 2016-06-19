package org.fs.qm.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.widget.RecyclerView
 */
public class RecyclerView extends android.support.v7.widget.RecyclerView {

    private final android.support.v7.widget.RecyclerView.OnScrollListener scrollTracker = new android.support.v7.widget.RecyclerView.OnScrollListener() {
        @Override public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
            if(scroll != null) {
                scroll.onScrolled(newState);
            }
        }

        @Override public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
            //ignored
        }
    };

    private OnScrollListener      scroll;
    private OnAttachStateListener listener;

    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnScrollListener(scrollTracker);
        if(listener != null) {
            listener.onAttached(this);
        }
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnScrollListener(scrollTracker);
        if(listener != null) {
            listener.onDetached(this);
        }
    }

    public void setOnAttachStateListener(OnAttachStateListener listener) {
        this.listener = listener;
    }

    public void setOnScroll(OnScrollListener scroll) {
        this.scroll = scroll;
    }

    public interface OnScrollListener {
        void onScrolled(int newState);
    }

    public interface OnAttachStateListener {
        void onAttached(View view);
        void onDetached(View view);
    }
}
