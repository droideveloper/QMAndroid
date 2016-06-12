package org.fs.qm.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.holders.MonthsAdapter
 */
public class MonthViewHolder extends AbstractViewHolder<String> {

    @BindView(value = android.R.id.text1) TextView textView;

    public MonthViewHolder(View view) {
        super(view);
        ButterKnife.bind(view);//we use butterKnife as injector
    }

    public void setData(@NonNull  String data) {
        if(!data.equalsIgnoreCase(this.data)) {
            this.data = data;
            onBindData(data);
        }
    }

    public String getData() {
        return this.data;
    }

    @Override protected void onBindData(String data) {
        setData(data);
        if(textView != null) {
            textView.setText(data);
        }
    }

    @Override
    protected String getClassTag() {
        return MonthViewHolder.class.getSimpleName();
    }

    @Override
    protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}