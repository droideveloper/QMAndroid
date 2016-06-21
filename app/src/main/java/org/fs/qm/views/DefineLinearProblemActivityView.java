package org.fs.qm.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import org.fs.core.AbstractActivity;
import org.fs.core.AbstractApplication;
import org.fs.qm.R;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.components.ApplicationComponent;
import org.fs.qm.entities.Objective;
import org.fs.qm.presenters.DefineLinearProblemActivityPresenter;
import org.fs.qm.presenters.IDefineLinearProblemActivityPresenter;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 22/06/16.
 * as org.fs.qm.views.DefineLinearProblemActivityView
 */
public class DefineLinearProblemActivityView extends AbstractActivity<IDefineLinearProblemActivityPresenter> implements IDefineLinearProblemActivityView {

    private CollapsingToolbarLayout toolbarLayout;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_define_lp_activity);

        savedInstanceState = new Bundle();
        savedInstanceState.putString("linear.problem.title", "[Untitled]");
        savedInstanceState.putString("linear.problem.objective", Objective.MAXIMIZE.name());
        savedInstanceState.putInt("linear.problem.row.count", 5 + 2);
        savedInstanceState.putInt("linear.problem.col.count", 3 + 3);
        savedInstanceState.putString("linear.problem.row.name.template", "Constraint%d");
        savedInstanceState.putString("linear.problem.col.name.template", "x%d");

        presenter.restoreState(savedInstanceState != null ? savedInstanceState : getIntent().getExtras());
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

    @Override public void setLinearProblemName(String nameStr) {
        toolbarLayout.setTitle(nameStr);
    }

    @Override public void onBindView() {
        Toolbar toolbar = ViewUtility.findViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(presenter.navigationListener());
        FloatingActionButton btnSolve = ViewUtility.findViewById(this, R.id.btnSolve);
        toolbarLayout = ViewUtility.findViewById(this, R.id.vgToolBar);
        btnSolve.setOnClickListener(presenter.clickListener());
    }

    @Override public void commit(Fragment frag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager != null) {
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.replace(R.id.fragDefineLinearProblem, frag);
            trans.commit();
        }
    }

    @Override public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.translate_in, R.anim.scale_out);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.scale_in, R.anim.translate_out);
    }

    @Override public boolean doesNeedNewCommit() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            return fragmentManager.findFragmentById(R.id.fragDefineLinearProblem) == null;
        }
        //dude we should not get here because... that means we are fucked up!
        return true;
    }

    @Override public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override protected IDefineLinearProblemActivityPresenter presenter() {
        return new DefineLinearProblemActivityPresenter(this);
    }

    @Override public ActivityComponent getActivityComponent() {
        //TODO
        return null;
    }

    @Override public ApplicationComponent getApplicationComponent() {
        //TODO
        return null;
    }

    @Override public Context getContext() {
        return this;
    }

    @Override protected String getClassTag() {
        return DefineLinearProblemActivityView.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}