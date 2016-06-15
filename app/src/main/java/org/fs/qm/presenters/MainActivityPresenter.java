package org.fs.qm.presenters;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.R;
import org.fs.qm.common.SimpleDrawerListener;
import org.fs.qm.views.IMainActivityView;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.presenters.MenuActivityPresenter
 */
public class MainActivityPresenter extends AbstractPresenter<IMainActivityView> implements IMainActivityPresenter,
                                                                                           NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_SELECTED_NAVIGATION_MENU = "navigation.selected.menu";
    private static final String KEY_SELECTED_NAV_TITLE       = "navigation.selected.name";
    private static final String KEY_DEFAULT_NAV_TITLE        = "navigation.default.name";

    private final static long   NAVIGATION_SELECTED_DELAY    = 250L;

    private final Handler   uiThreadHandler  = new Handler(Looper.myLooper());
    private final Runnable  navigationThread = new Runnable() {
        @Override public void run() {
            if(view.isAvailable()) {
                final MenuItem navigation = view.findMenuItem(selectedNavigationMenu);
                if(navigation != null) {
                    navigate(navigation);
                }
            }
        }
    };

    private SimpleDrawerListener simpleDrawerListener = new SimpleDrawerListener() {

        @Override public void onDrawerClosed(View drawerView) {
            view.setTitle(selectedNavigationTitle);
        }

        @Override public void onDrawerOpened(View drawerView) {
            view.setTitle(defaultNavigationTitle);
        }
    };

    private int     selectedNavigationMenu;
    private String  selectedNavigationTitle;
    private String  defaultNavigationTitle;

    public MainActivityPresenter(IMainActivityView view) {
        super(view);
    }

    @Override public void restoreState(Bundle input) {
        if(input != null) {
            selectedNavigationMenu = input.getInt(KEY_SELECTED_NAVIGATION_MENU);
            selectedNavigationTitle = input.getString(KEY_SELECTED_NAV_TITLE);
            defaultNavigationTitle = input.getString(KEY_DEFAULT_NAV_TITLE);
        }
    }

    @Override public void storeState(Bundle output) {
        output.putInt(KEY_SELECTED_NAVIGATION_MENU, selectedNavigationMenu);
        if(selectedNavigationTitle != null) {
            output.putString(KEY_SELECTED_NAV_TITLE, selectedNavigationTitle);
        }
        if(defaultNavigationTitle != null) {
            output.putString(KEY_DEFAULT_NAV_TITLE, defaultNavigationTitle);
        }
    }

    @Override public boolean optionsItemSelected(MenuItem item) {
        if(view.shouldDrawerToggleSelected(item)) {
            return true;
        }
        return view.shouldActivitySelected(item);
    }

    @Override public boolean hasBackPress() {
        boolean hasDrawerOpen = view.hasDrawerOpen();
        if(hasDrawerOpen) {
            view.closeMenu();
            return true;
        }
        FragmentManager fragmentManager = view.getSupportFragmentManager();
        return fragmentManager.popBackStackImmediate();
    }

    @Override public boolean onNavigationItemSelected(MenuItem navigation) {
        boolean alreadySelected = selectedNavigationMenu == navigation.getItemId();
        if(!alreadySelected) {
            selectedNavigationMenu = navigation.getItemId();
            selectedNavigationTitle = navigation.getTitle().toString();//title is charSequence

            uiThreadHandler.postDelayed(navigationThread, NAVIGATION_SELECTED_DELAY);
        }
        view.closeMenu();
        return true;
    }

    @Override public void navigate(MenuItem navigation) {
        //todo implement this navigation change
    }

    @Override public void onCreate() {
        view.setUpViews();
        //grab default title
        if(defaultNavigationTitle == null) {
            defaultNavigationTitle = view.getContext().getString(R.string.nv_drawer_title);
        }
        //load default selected
        if(selectedNavigationMenu == 0) {
            view.setNavigationSelected(R.id.nvHome);
        }
    }

    @Override public void onStart() {
        if(selectedNavigationMenu != 0) {
            view.setNavigationSelected(selectedNavigationMenu);
        }
    }

    @Override public void onStop() {
        uiThreadHandler.removeCallbacks(navigationThread);
    }

    @Override public void onDestroy() {
        uiThreadHandler.removeCallbacks(navigationThread);
    }

    @Override public NavigationView.OnNavigationItemSelectedListener provideNavigationListener() {
        return this;
    }

    @Override public SimpleDrawerListener provideSimpleDrawerListener() {
        return simpleDrawerListener;
    }

    @Override protected String getClassTag() {
        return MainActivityPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

}