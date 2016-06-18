package org.fs.qm.holders;

import android.view.View;

import org.fs.qm.entities.TextCell;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.TextTypeHolder
 */
public class TextTypeHolder extends BaseTypeHolder<TextCell> {

    public TextTypeHolder(View view) {
        super(view);
    }

    @Override protected String getClassTag() {
        return TextTypeHolder.class.getSimpleName();
    }

    @Override protected void onBindView(TextCell data) {
        super.onBindView(data);
    }
}
