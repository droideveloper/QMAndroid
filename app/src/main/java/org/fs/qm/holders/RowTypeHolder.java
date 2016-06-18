package org.fs.qm.holders;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.fs.anim.CoreInterpolator;
import org.fs.common.BusManager;
import org.fs.core.AbstractApplication;
import org.fs.core.AbstractRecyclerViewHolder;
import org.fs.qm.adapters.ColumnRecyclerAdapter;
import org.fs.qm.common.SimpleAnimatorListener;
import org.fs.qm.entities.ICellEntity;
import org.fs.qm.events.ColumnScrollEvent;
import org.fs.qm.widget.RecyclerView;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.RowTypeHolder
 */
public class RowTypeHolder extends AbstractRecyclerViewHolder<List<ICellEntity>> implements Action1<Object>, RecyclerView.OnAttachStateListener {

    private final static long   ANIMATION_TIME    = 300L;
    private final static String PROPERTY_SCROLL_X = "scrollX";

    private final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //ignored
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //this is what we have here, we send this event then
            //however we create infinite cycle this way so
            //before animation or whatever it's we remove listener
            //then put listener back after we complete those
            busManager.post(new ColumnScrollEvent(dx, dy));
        }
    };

    private   RecyclerView          recyclerView;
    private   Subscription          busListener;
    protected List<ICellEntity>     data;
    private   BusManager            busManager;

    public RowTypeHolder(View view, BusManager busManager) {
        super(view);
        this.busManager = busManager;
        recyclerView = (RecyclerView) view;
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setOnAttachStateListener(this);
    }

    @Override protected String getClassTag() {
        return RowTypeHolder.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override protected void onBindView(List<ICellEntity> data) {
        ColumnRecyclerAdapter columnAdapter = new ColumnRecyclerAdapter(data, itemView.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(columnAdapter);
    }

    //call this
    public void notifyDataSet(List<ICellEntity> data) {
        this.data = data;
        onBindView(data);
    }

    void scrollBy(int dx,  boolean animated) {
        unregisterBus();
        if(animated) {
            recyclerView.clearAnimation();

            ObjectAnimator effect = ObjectAnimator.ofInt(itemView, PROPERTY_SCROLL_X, itemView.getScrollX(), dx);
            effect.setInterpolator(new CoreInterpolator());
            effect.setDuration(ANIMATION_TIME);

            effect.addListener(new SimpleAnimatorListener() {
                @Override public void onAnimationEnd(Animator animation) {
                    registerBus();
                }
            });
            effect.start();
        } else {
            recyclerView.scrollTo(dx, 0);
            registerBus();
        }
    }

    void registerBus() {
        busListener = busManager.register(this);
    }

    void unregisterBus() {
        if(busListener != null) {
            busManager.unregister(busListener);
            busListener = null;
        }
    }

    @Override public void call(Object e) {
        if(e instanceof ColumnScrollEvent) {
            ColumnScrollEvent event = (ColumnScrollEvent) e;
            scrollBy(event.dx, Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN);
        }
    }

    @Override public void onAttached(View view) {
       registerBus();
    }

    @Override public void onDetached(View view) {
        unregisterBus();
    }
}