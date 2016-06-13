package org.fs.qm.presenters;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.views.IMainActivityView;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.presenters.MenuActivityPresenter
 */
public class MenuActivityPresenter extends AbstractPresenter<IMainActivityView> implements IMainActivityPresenter,
                                                                                           NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_SELECTED_NAVIGATION_MENU = "navigation.selected.menu";

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

    private int selectedNavigationMenu;

    public MenuActivityPresenter(IMainActivityView view) {
        super(view);
    }

    @Override public void restoreState(Bundle input) {
        if(input != null) {
            selectedNavigationMenu = input.getInt(KEY_SELECTED_NAVIGATION_MENU);
        }
    }

    @Override public void storeState(Bundle output) {
        output.putInt(KEY_SELECTED_NAVIGATION_MENU, selectedNavigationMenu);
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

    @Override protected String getClassTag() {
        return MenuActivityPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

}