package org.fs.qm.holders;

import android.view.View;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractRecyclerViewHolder;
import org.fs.qm.entities.ICellEntity;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.BaseTypeHolder
 */
public class BaseTypeHolder<T extends ICellEntity> extends AbstractRecyclerViewHolder<T> implements IBaseTypeHolder {

    protected T data;

    public BaseTypeHolder(View view) {
        super(view);
    }

    @Override protected String getClassTag() {
        return BaseTypeHolder.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override protected void onBindView(T data) {
        //we gone use super type
    }

    public final void notifyDataSet(T data) {
        this.data = data;
        onBindView(data);
    }
}