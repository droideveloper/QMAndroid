package org.fs.qm.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractRecyclerAdapter;
import org.fs.exception.AndroidException;
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
import org.fs.qm.R;

import java.lang.ref.WeakReference;
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

    private WeakReference<FragmentManager> fragmentManagerRef;

    public ColumnRecyclerAdapter(List<ICellEntity> dataSet, Context context, FragmentManager fragmentManager) {
        super(dataSet, context);
        fragmentManagerRef = fragmentManager != null ? new WeakReference<>(fragmentManager) : null;
    }

    @Override public BaseTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater factory = inflaterFactory();
        if(factory != null) {
            if (viewType == TYPE_EMPTY) {
                return new EmptyTypeHolder(factory.inflate(R.layout.widget_empty_cell, parent, false));
            } else if (viewType == TYPE_BOUND) {
                return new BoundTypeHolder(factory.inflate(R.layout.widget_bound_cell, parent, false));
            } else if (viewType == TYPE_LABEL) {
                return new LabelTypeHolder(factory.inflate(R.layout.widget_label_cell, parent, false));
            } else if (viewType == TYPE_TEXT) {
                return new TextTypeHolder(factory.inflate(R.layout.widget_text_cell, parent, false), getFragmentManager());
            }
            throw new AndroidException("no such viewType we ve been expecting");
        }
        throw new AndroidException("we can not have instance of inflater since our context is death");
    }

    @Override public void onBindViewHolder(BaseTypeHolder holder, int position) {
        if(holder instanceof EmptyTypeHolder) {
            EmptyTypeHolder castedHolder = (EmptyTypeHolder) holder;
            castedHolder.notifyDataSet((EmptyCell) getItemAt(position));
        } else if(holder instanceof BoundTypeHolder) {
            BoundTypeHolder castedHolder = (BoundTypeHolder) holder;
            castedHolder.notifyDataSet((BoundCell) getItemAt(position));
        } else if(holder instanceof LabelTypeHolder) {
            LabelTypeHolder castedHolder = (LabelTypeHolder) holder;
            castedHolder.notifyDataSet((LabelCell) getItemAt(position));
        } else if(holder instanceof TextTypeHolder) {
            TextTypeHolder castedHolder = (TextTypeHolder) holder;
            castedHolder.notifyDataSet((TextCell) getItemAt(position));
        }
    }

    @Override public int getItemViewType(int position) {
        ICellEntity entity = getItemAt(position);
        if(entity instanceof EmptyCell) {
            return TYPE_EMPTY;
        } else if(entity instanceof BoundCell) {
            return TYPE_BOUND;
        } else if(entity instanceof LabelCell) {
            return TYPE_LABEL;
        } else if(entity instanceof TextCell) {
            return TYPE_TEXT;
        }
        throw new AndroidException("we can not recognize type or object is not what we expected");
    }

    @Override protected String getClassTag() {
        return ColumnRecyclerAdapter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    FragmentManager getFragmentManager() {
        return fragmentManagerRef != null ? fragmentManagerRef.get() : null;
    }
}