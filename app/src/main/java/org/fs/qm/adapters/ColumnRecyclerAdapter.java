package org.fs.qm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractRecyclerAdapter;
import org.fs.qm.entities.BoundCell;
import org.fs.qm.entities.EmptyCell;
import org.fs.qm.entities.ICellEntity;
import org.fs.qm.entities.LabelCell;
import org.fs.qm.entities.TextCell;
import org.fs.qm.holders.BaseTypeHolder;
import org.fs.qm.holders.BoundTypeHolder;
import org.fs.qm.holders.EmptyTypeHolder;
import org.fs.qm.holders.LabelTypeHolder;
import org.fs.qm.holders.TextTypeHolder;

import java.util.List;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.adapters.ColumnRecyclerAdapter
 */
public class ColumnRecyclerAdapter extends AbstractRecyclerAdapter<ICellEntity, BaseTypeHolder> {

    public static final int TYPE_EMPTY = 0x1;
    public static final int TYPE_BOUND = 0x2;
    public static final int TYPE_TEXT  = 0x3;
    public static final int TYPE_LABEL = 0x4;

    public ColumnRecyclerAdapter(List<ICellEntity> dataSet, Context context) {
        super(dataSet, context);
    }

    @Override public BaseTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater factory = LayoutInflater.from(getContext());
        if(viewType == TYPE_EMPTY) {
            //todo inflate views
            return new EmptyTypeHolder(null);
        } else if(viewType == TYPE_BOUND) {
            //todo inflate views
            return new BoundTypeHolder(null);
        } else if(viewType == TYPE_LABEL) {
            //todo inflate views
            return new LabelTypeHolder(null);
        } else if(viewType == TYPE_TEXT) {
            //todo inflate views
            return new TextTypeHolder(null);
        }
        return null;
    }

    @Override public void onBindViewHolder(BaseTypeHolder holder, int position) {
        if(holder instanceof EmptyTypeHolder) {
            EmptyTypeHolder castedHolder = (EmptyTypeHolder) holder;
            castedHolder.notifyDataSet((EmptyCell) getItemAtIndex(position));
        } else if(holder instanceof BoundTypeHolder) {
            BoundTypeHolder castedHolder = (BoundTypeHolder) holder;
            castedHolder.notifyDataSet((BoundCell) getItemAtIndex(position));
        } else if(holder instanceof LabelTypeHolder) {
            LabelTypeHolder castedHolder = (LabelTypeHolder) holder;
            castedHolder.notifyDataSet((LabelCell) getItemAtIndex(position));
        } else if(holder instanceof TextTypeHolder) {
            TextTypeHolder castedHolder = (TextTypeHolder) holder;
            castedHolder.notifyDataSet((TextCell) getItemAtIndex(position));
        }
    }

    @Override public int getItemViewType(int position) {
        ICellEntity entity = getItemAtIndex(position);
        if(entity instanceof EmptyCell) {
            return TYPE_EMPTY;
        } else if(entity instanceof BoundCell) {
            return TYPE_BOUND;
        } else if(entity instanceof LabelCell) {
            return TYPE_LABEL;
        } else if(entity instanceof TextCell) {
            return TYPE_TEXT;
        }
        return 0;
    }

    @Override protected String getClassTag() {
        return ColumnRecyclerAdapter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}