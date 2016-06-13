package org.fs.qm.views;

import android.content.Context;
import android.view.View;

import org.fs.common.IView;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.components.ApplicationComponent;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.views.ICreateLinearProblemView
 */
public interface ICreateLinearProblemFragmentView extends IView {

    void        setUpViews();

    String      getTitle();
    int         getType();
    int         getRowCount();
    int         getColumnCount();
//    String      getRowName(); these will be hold at presenter
//    String      getColumnName();

    void        setTitle(String title);
//    void        setType(int type);
    void        setRowCount(int progress);
    void        setRowCount(String progress);
    void        setColumnCount(int progress);
    void        setColumnCount(String progress);

    View                    onBindView(View view);
    ApplicationComponent    getApplicationComponent();
    ActivityComponent       getActivityComponent();
    boolean                 isAvailable();
    Context                 getContext();
}