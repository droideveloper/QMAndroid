package org.fs.qm.presenters;

import android.support.v7.widget.Toolbar;
import android.view.View;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.views.ICreateLinearProblemActivityView;

/**
 * Created by Fatih on 15/06/16.
 * as org.fs.qm.presenters.CreateLinearProblemActivityPresenter
 */
public class CreateLinearProblemActivityPresenter extends AbstractPresenter<ICreateLinearProblemActivityView> implements ICreateLinearProblemActivityPresenter,
                                                                                                                         Toolbar.OnClickListener {

    public CreateLinearProblemActivityPresenter(ICreateLinearProblemActivityView view) {
        super(view);
    }

    @Override public void onClick(View v) {
        view.finish();
    }

    @Override
    public void onBackPressed() {
        view.finish();
    }

    @Override public Toolbar.OnClickListener provideNavigationClickListener() {
        return this;
    }

    @Override public void onCreate() {
        view.onBindView();
    }

    @Override public void onStart() {
        //no op
    }

    @Override public void onStop() {
        //no op
    }

    @Override public void onDestroy() {
        //no op
    }

    @Override protected String getClassTag() {
        return CreateLinearProblemActivityPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}