package org.fs.qm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractListAdapter;
import org.fs.exception.AndroidException;
import org.fs.qm.holders.BoundViewHolder;

import java.util.List;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.adapters.BoundAdapter
 */
public class BoundAdapter extends AbstractListAdapter<String, BoundViewHolder> {

    public BoundAdapter(Context context) {
        super(context);
    }

    public BoundAdapter(Context context, List<String> dataSet) {
        super(context, dataSet);
    }

    @Override protected BoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater factory = inflaterFactory();
        if(factory != null) {
            View view = factory.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new BoundViewHolder(view);
        }
        throw new AndroidException("you can not grab inflater, since context is death.");
    }

    @Override protected void onBindViewHolder(BoundViewHolder viewHolder, int position) {
        final String string = getItemAt(position);
        if(string != null) {
            viewHolder.notifyDataSet(string);
        }
    }

    @Override protected String getClassTag() {
        return BoundAdapter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}