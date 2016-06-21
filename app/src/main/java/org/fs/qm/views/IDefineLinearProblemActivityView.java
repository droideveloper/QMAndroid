package org.fs.qm.views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import org.fs.common.IView;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.components.ApplicationComponent;

/**
 * Created by Fatih on 22/06/16.
 * as org.fs.qm.views.IDefineLinearProblemActivityView
 */
public interface IDefineLinearProblemActivityView extends IView {

    void startActivity(Intent intent);
    void finish();
    void setLinearProblemName(String nameStr);
    void onBindView();
    void onBackPressed();

    void commit(Fragment frag);

    boolean              doesNeedNewCommit();
    ApplicationComponent getApplicationComponent();
    ActivityComponent    getActivityComponent();
    Context              getContext();
}