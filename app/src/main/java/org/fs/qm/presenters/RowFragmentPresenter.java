package org.fs.qm.presenters;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.R;
import org.fs.qm.views.IRowFragmentView;

import java.util.Arrays;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.presenters.RowFragmentPresenter
 */
public class RowFragmentPresenter extends AbstractPresenter<IRowFragmentView> implements IRowFragmentPresenter,
                                                                                         RadioGroup.OnCheckedChangeListener,
                                                                                         Spinner.OnItemSelectedListener {

    public RowFragmentPresenter(IRowFragmentView view) {
        super(view);
    }

    @Override public void restoreState(Bundle restore) {
        //no op
    }

    @Override public void storeState(Bundle store) {
        //no op
    }

    @Override public RadioGroup.OnCheckedChangeListener provideCheckedChangeListener() {
        return this;
    }

    @Override public Spinner.OnItemSelectedListener provideItemSelectedListener() {
        return this;
    }

    @Override public void onCreate() {
        if(view.isAvailable()) {
            view.onSetupViews();
            view.disableMonth();
        }
    }

    @Override public void onStart() {
        if(view.isAvailable()) {
            view.setData(Arrays.asList(readArray(R.array.months)));
        }
    }

    @Override public String[] readArray(@ArrayRes int resId) {
        return view.getContext().getResources().getStringArray(resId);
    }

    @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.rbMonth) {
            view.enableMonth();
        } else {
            view.disableMonth();
        }
        //todo implement other logic
    }

    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //todo implement what to do
    }

    @Override public void onNothingSelected(AdapterView<?> parent) {
        //todo needs implement?
    }

    @Override public void onStop() {
        //no op
    }

    @Override public void onDestroy() {
        //no op
    }

    @Override protected String getClassTag() {
        return RowFragmentPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}