package org.fs.qm.views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import org.fs.common.IView;
import org.fs.qm.QMAndroidApplication;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.components.ApplicationComponent;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.views.IMainActivityView
 */
public interface IMainActivityView extends IView {


    void setUpViews();
    void onBindView();
    //handset
    void closeMenu();
    void showMenu();
    void setNavigationSelected(int id);
    void setTitle(String title);
    //tablet
    void commitWithAnim(Fragment fragment);
    void commitWithoutAnim(Fragment fragment);
    //handset
    void startActivity(Intent intent);
    void finish();

    FragmentManager      getSupportFragmentManager();
    ApplicationComponent getApplicationComponent();
    ActivityComponent    getActivityComponent();
    QMAndroidApplication getQMAndroidApplication();


    boolean              shouldDrawerToggleSelected(MenuItem item);
    boolean              shouldActivitySelected(MenuItem item);
    MenuItem             findMenuItem(int id);
    boolean              isTablet();
    boolean              hasDrawerOpen();
    boolean              isAvailable();
    Context              getContext();

}