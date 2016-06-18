package org.fs.qm.holders;

import android.view.View;

import org.fs.qm.entities.EmptyCell;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.EmptyTypeHolder
 */
public class EmptyTypeHolder extends BaseTypeHolder<EmptyCell> {

    public EmptyTypeHolder(View view) {
        super(view);
    }

    @Override protected String getClassTag() {
        return EmptyTypeHolder.class.getSimpleName();
    }

    @Override protected void onBindView(EmptyCell data) {
        super.onBindView(data);
    }
}