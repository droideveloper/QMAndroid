package org.fs.qm.holders;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import org.fs.common.BusManager;
import org.fs.core.AbstractRecyclerViewHolder;
import org.fs.qm.adapters.ColumnRecyclerAdapter;
import org.fs.qm.entities.ICellEntity;
import org.fs.qm.events.ColumnScrollEvent;
import org.fs.qm.widget.RecyclerView;
import org.fs.util.ViewUtility;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.RowTypeHolder
 */
public class RowTypeHolder extends AbstractRecyclerViewHolder<List<ICellEntity>> implements Action1<Object>,
                                                                                            RecyclerView.OnAttachStateListener,
                                                                                            RecyclerView.OnScrollListener {

    private final static long   ANIMATION_TIME    = 300L;
    private final static String PROPERTY_SCROLL_X = "scrollX";

    private   LinearLayoutManager   layoutManager;
    private   RecyclerView          recyclerView;
    private   Subscription          busListener;
    protected List<ICellEntity>     data;
    private   BusManager            busManager;
    private WeakReference<FragmentManager> fragmentRef;

    public RowTypeHolder(View view, BusManager busManager, FragmentManager fragmentManager) {
        super(view);
        this.fragmentRef = fragmentManager != null ? new WeakReference<>(fragmentManager) : null;
        this.busManager = busManager;
        recyclerView = ViewUtility.castAsField(view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setOnAttachStateListener(this);
        layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnScroll(this);
    }

    @Override protected String getClassTag() {
        return RowTypeHolder.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return Boolean.TRUE;
    }

    @Override protected void onBindView(List<ICellEntity> data) {
        ColumnRecyclerAdapter columnAdapter = new ColumnRecyclerAdapter(data, itemView.getContext(), getFragmentManager());
        recyclerView.setAdapter(columnAdapter);
    }

    //call this
    public void notifyDataSet(List<ICellEntity> data) {
        this.data = data;
        onBindView(data);
    }

    //TODO find a way to sync those view when scrolled
    //however this option needs to be carefully handled because we can not sync properly at the moment
    void scrollBy(int dx,  boolean animated) {
        //todo scroll to position
//        unregisterBus();
//        if(animated) {
//            recyclerView.clearAnimation();
//
//            ObjectAnimator effect = ObjectAnimator.ofInt(itemView, PROPERTY_SCROLL_X, itemView.getScrollX(), dx);
//            effect.setInterpolator(new CoreInterpolator());
//            effect.setDuration(ANIMATION_TIME);
//
//            effect.addListener(new SimpleAnimatorListener() {
//                @Override public void onAnimationEnd(Animator animation) {
//                    registerBus();
//                }
//            });
//            effect.start();
//        } else {
//
//            registerBus();
//        }
        //unregisterBus();
        //recyclerView.scrollToPosition(dx);
        //registerBus();
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
            scrollBy(event.dx, true);
            log(Log.ERROR, String.format(Locale.ENGLISH, "D:/%d - %d", getAdapterPosition(), event.dx));
        }
    }

    @Override public void onScrolled(int newState) {
        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
            //busManager.post(new ColumnScrollEvent(getAdapterPosition(), newState));
            log(Log.ERROR, String.format(Locale.ENGLISH, "E:/%d - %d", getAdapterPosition(), newState));
        }
    }

    @Override public void onAttached(View view) {
       registerBus();
    }

    @Override public void onDetached(View view) {
        unregisterBus();
    }

    FragmentManager getFragmentManager() {
        return fragmentRef != null ? fragmentRef.get() : null;
    }
}