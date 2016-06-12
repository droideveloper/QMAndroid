package org.fs.qm.views;

import android.content.Context;

import org.fs.common.IView;

import java.util.List;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.views.IColumnFragmentView
 */
public interface IColumnFragmentView extends IView {

    void onSetupViews();
    void setData(List<String> dataSet);

    void disableMonth();
    void enableMonth();

    boolean isAvailable();
    Context getContext();
}