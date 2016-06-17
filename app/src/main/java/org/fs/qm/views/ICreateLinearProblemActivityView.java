package org.fs.qm.views;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import org.fs.common.IView;
import org.fs.qm.QMAndroidApplication;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.components.ApplicationComponent;

/**
 * Created by Fatih on 15/06/16.
 * as org.fs.qm.views.ICreateLinearProblemActivityView
 */
public interface ICreateLinearProblemActivityView extends IView {

    void setTitle(String titleStr);
    void startActivity(Intent intent);
    void finish();
    void onBindView();

    void showError(Snackbar errorMsg);
    void hideError(Snackbar errorMsg);

    ApplicationComponent                getApplicationComponent();
    ActivityComponent                   getActivityComponent();
    QMAndroidApplication                getQMAndroidApplication();
    ICreateLinearProblemFragmentView    getCreateLinearProblemFragmentView();
    Context                             getContext();
}