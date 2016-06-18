package org.fs.qm.holders;

import android.view.View;
import android.widget.TextView;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractViewHolder;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.BoundViewHolder
 */
public class BoundViewHolder extends AbstractViewHolder<String> {

    private TextView textView;

    public BoundViewHolder(View view) {
        super(view);
        textView = ViewUtility.castAsField(view);
    }

    @Override protected void onBindData(String data) {
        if(textView != null) {
            textView.setText(data);
        }
    }

    public final void notifyDataSet(String data) {
        this.data = data;
        onBindData(data);
    }

    @Override protected String getClassTag() {
        return BoundViewHolder.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}