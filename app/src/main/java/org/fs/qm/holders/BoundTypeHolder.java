package org.fs.qm.holders;

import android.view.View;

import org.fs.qm.entities.BoundCell;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.BoundTypeHolder
 */
public class BoundTypeHolder extends BaseTypeHolder<BoundCell> {

    public BoundTypeHolder(View view) {
        super(view);
    }

    @Override protected String getClassTag() {
        return BoundTypeHolder.class.getSimpleName();
    }

    @Override protected void onBindView(BoundCell data) {
        super.onBindView(data);
    }
}
