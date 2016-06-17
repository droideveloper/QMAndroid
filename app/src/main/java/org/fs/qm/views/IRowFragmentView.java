package org.fs.qm.views;

import android.content.Context;
import android.view.View;

import org.fs.common.IView;

import java.util.List;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.views.IRowsFragmentView
 */
public interface IRowFragmentView extends IView {

    void onSetupViews();
    void setData(List<String> dataSet);

    void disableMonth();
    void enableMonth();

    View    onBindView(View view);
    boolean isAvailable();
    Context getContext();
}