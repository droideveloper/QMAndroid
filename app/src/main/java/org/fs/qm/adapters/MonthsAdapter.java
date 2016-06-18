package org.fs.qm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractListAdapter;
import org.fs.exception.AndroidException;
import org.fs.qm.holders.MonthViewHolder;

import java.util.List;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.adapters.MonthsAdapter
 */
public class MonthsAdapter extends AbstractListAdapter<String, MonthViewHolder> {

    public MonthsAdapter(Context context) {
        super(context);
    }

    public MonthsAdapter(Context context, List<String> dataSet) {
        super(context, dataSet);
    }

    @Override protected MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater factory = inflaterFactory();
        if(factory != null) {
            View view = factory.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MonthViewHolder(view);
        }
        throw new AndroidException("we can not grab instance of factory of LayoutInflater");
    }

    @Override protected void onBindViewHolder(MonthViewHolder viewHolder, int position) {
        final String str = getItemAt(position);
        if(str != null) {
            viewHolder.notifyDataSet(str);
        }
    }

    @Override protected String getClassTag() {
        return MonthsAdapter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}