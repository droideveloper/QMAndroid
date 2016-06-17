package org.fs.qm.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import org.fs.core.AbstractActivity;
import org.fs.core.AbstractApplication;
import org.fs.qm.QMAndroidApplication;
import org.fs.qm.R;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.components.ApplicationComponent;
import org.fs.qm.presenters.CreateLinearProblemActivityPresenter;
import org.fs.qm.presenters.ICreateLinearProblemActivityPresenter;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 15/06/16.
 * as org.fs.qm.views.CreateLinearProblemActivityView
 */
public class CreateLinearProblemActivityView extends AbstractActivity<ICreateLinearProblemActivityPresenter> implements ICreateLinearProblemActivityView {

    private Toolbar                          toolBar;
    private CollapsingToolbarLayout          toolBarLayout;
    private ICreateLinearProblemFragmentView fragCreateLinearProblem;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_lp_activity);
        presenter.onCreate();
    }

    @Override protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.translate_in, R.anim.scale_out);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.scale_in, R.anim.translate_out);
    }

    @Override public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override public void onBindView() {
        toolBarLayout  = ViewUtility.findViewById(this, R.id.vgToolBar);
        toolBar = ViewUtility.findViewById(this, R.id.toolbar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolBar.setNavigationOnClickListener(presenter.provideNavigationClickListener());
    }

    @Override public void setTitle(String titleStr) {
        if(toolBar != null) {
            toolBar.setTitle(titleStr);
        }
        if(toolBarLayout != null) {
            toolBarLayout.setTitle(titleStr);
        }
    }

    @Override public void showError(Snackbar errorMsg) {
        if(errorMsg != null) {
            errorMsg.show();
        }
    }

    @Override public void hideError(Snackbar errorMsg) {
        if(errorMsg != null) {
            errorMsg.dismiss();
        }
    }

    @Override protected ICreateLinearProblemActivityPresenter presenter() {
        return new CreateLinearProblemActivityPresenter(this);
    }

    @Override public ICreateLinearProblemFragmentView getCreateLinearProblemFragmentView() {
        if(fragCreateLinearProblem == null) {
            fragCreateLinearProblem = (ICreateLinearProblemFragmentView) getSupportFragmentManager().findFragmentById(R.id.fragCreateLinearProblem);
        }
        return fragCreateLinearProblem;
    }

    @Override public ApplicationComponent getApplicationComponent() {
        // TODO: implement 
        return null;
    }

    @Override public ActivityComponent getActivityComponent() {
        // TODO: implement
        return null;
    }

    @Override public QMAndroidApplication getQMAndroidApplication() {
        return (QMAndroidApplication) getApplication();
    }

    @Override public Context getContext() {
        return this;
    }

    @Override protected String getClassTag() {
        return CreateLinearProblemActivityView.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}