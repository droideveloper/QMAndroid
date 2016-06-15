package org.fs.qm.presenters;

import android.support.v7.widget.Toolbar;

import org.fs.common.IPresenter;

/**
 * Created by Fatih on 15/06/16.
 * as org.fs.qm.presenters.ICreateLinearProblemActivityPresenter
 */
public interface ICreateLinearProblemActivityPresenter extends IPresenter {

    void onBackPressed();

    Toolbar.OnClickListener  provideNavigationClickListener();
}