package org.fs.qm.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.fs.core.AbstractActivity;
import org.fs.core.AbstractApplication;
import org.fs.qm.QMAndroidApplication;
import org.fs.qm.R;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.components.ApplicationComponent;
import org.fs.qm.presenters.IMainActivityPresenter;
import org.fs.qm.presenters.MainActivityPresenter;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.presenters.MainActivityView
 */
public class MainActivityView extends AbstractActivity<IMainActivityPresenter> implements IMainActivityView {

    private Toolbar                 toolBar;
    private NavigationView          navigationView;
    private DrawerLayout            drawerLayout;
    private ActionBarDrawerToggle   drawerToggle;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.layout_main_activity);
        onBindView();
        presenter.restoreState(savedInstanceState != null ? savedInstanceState : getIntent().getExtras());
        presenter.onCreate();
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.restoreState(savedInstanceState);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.storeState(outState);
    }

    @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(!isTablet()) {
            drawerToggle.syncState();
        }
    }

    @Override public boolean shouldActivitySelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean shouldDrawerToggleSelected(MenuItem item) {
        return drawerToggle != null && drawerToggle.onOptionsItemSelected(item);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.optionsItemSelected(item);
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

    @Override public void onBindView() {
        toolBar         = ViewUtility.findViewById(this, R.id.toolbar);
        navigationView  = ViewUtility.findViewById(this, R.id.nvMenu);
        if (!isTablet()) {//non tablet ui
            drawerLayout = ViewUtility.findViewById(this, R.id.vgDrawer);
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        }
    }

    @Override public void setUpViews() {
        if(!isTablet()) {//non tablet configuration
            setSupportActionBar(toolBar);
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
            }
            drawerLayout.addDrawerListener(drawerToggle);
            drawerLayout.addDrawerListener(presenter.provideSimpleDrawerListener());//we just used it for text
        }
        navigationView.setNavigationItemSelectedListener(presenter.provideNavigationListener());
    }

    @Override public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.translate_in, R.anim.scale_out);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
    }

    @Override public void setTitle(String title) {
        toolBar.setTitle(title);
    }

    @Override public ApplicationComponent getApplicationComponent() {
        return null;
    }

    @Override public ActivityComponent getActivityComponent() {
        return null;
    }

    @Override public QMAndroidApplication getQMAndroidApplication() {
        return (QMAndroidApplication) getApplication();
    }

    @Override public void commitWithAnim(Fragment fragment) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.vgContainer, fragment);
        trans.setCustomAnimations(R.anim.translate_in, R.anim.scale_out,
                                  R.anim.scale_in, R.anim.translate_out);
        //trans.addToBackStack(String.valueOf(fragment.hashCode())); if we want to navigate with all the way around only with fragments then it's ok but if we don't ?
        trans.commit();
    }

    @Override public void commitWithoutAnim(Fragment fragment) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.vgContainer, fragment);
        //trans.addToBackStack(String.valueOf(fragment.hashCode())); if we want to navigate with all the way around only with fragments then it's ok but if we don't ?
        trans.commit();
    }

    @Override public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    @Override public Context getContext() {
        return this;
    }

    @Override public void onBackPressed() {
        boolean hasBackPress = presenter.hasBackPress();
        if(!hasBackPress) {
            //presenter not need
            super.onBackPressed();
        }
    }

    @Override public void closeMenu() {
        if(!isTablet()) {//we handle table concept in here
            if (drawerLayout != null) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    @Override public void showMenu() {
        if(!isTablet()) {
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        }
    }

    @Override public void setNavigationSelected(int id) {
        final MenuItem item = findMenuItem(id);
        if(item != null) {
            item.setChecked(true);
        }
    }

    @Override public MenuItem findMenuItem(int id) {
        return navigationView.getMenu().findItem(id);
    }

    @Override public boolean isAvailable() {
        return !isFinishing();
    }

    @Override public boolean hasDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override protected IMainActivityPresenter presenter() {
        return new MainActivityPresenter(this);
    }

    @Override protected String getClassTag() {
        return MainActivityView.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}