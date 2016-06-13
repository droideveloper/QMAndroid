package org.fs.qm.presenters;

import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.SeekBar;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.views.ICreateLinearProblemFragmentView;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.presenters.CreateLinearProblemFragmentPresenter
 */
public class CreateLinearProblemFragmentPresenter extends AbstractPresenter<ICreateLinearProblemFragmentView> implements ICreateLinearProblemFragmentPresenter {

    public CreateLinearProblemFragmentPresenter(ICreateLinearProblemFragmentView view) {
        super(view);
    }

    @Override
    public void restoreState(Bundle input) {

    }

    @Override
    public void storeState(Bundle output) {

    }

    @Override
    public TextWatcher provideTitleWatcher() {
        return null;
    }

    @Override
    public TextWatcher provideRowWatcher() {
        return null;
    }

    @Override
    public TextWatcher provideColumnWatcher() {
        return null;
    }

    @Override
    public SeekBar.OnSeekBarChangeListener provideRowChangeListener() {
        return null;
    }

    @Override
    public SeekBar.OnSeekBarChangeListener provideColumnChangeListener() {
        return null;
    }

    @Override
    public void onCreate() {
        if(view.isAvailable()) {
            view.setUpViews();
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override protected String getClassTag() {
        return CreateLinearProblemFragmentPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}