package org.fs.qm.presenters;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import org.fs.common.IPresenter;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.presenters.IMainActivityPresenter
 */
public interface IMainActivityPresenter extends IPresenter {

    void restoreState(Bundle input);
    void storeState(Bundle output);

    boolean hasBackPress();
    void    navigate(MenuItem navigation);

    NavigationView.OnNavigationItemSelectedListener provideNavigationListener();
}