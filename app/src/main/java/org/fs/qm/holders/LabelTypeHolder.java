package org.fs.qm.holders;

import android.view.View;

import org.fs.qm.entities.LabelCell;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.LabelTypeHolder
 */
public class LabelTypeHolder extends BaseTypeHolder<LabelCell> {

    public LabelTypeHolder(View view) {
        super(view);
    }

    @Override protected String getClassTag() {
        return LabelTypeHolder.class.getSimpleName();
    }

    @Override protected void onBindView(LabelCell data) {
        super.onBindView(data);
    }
}
