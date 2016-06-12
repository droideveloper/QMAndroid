package org.fs.qm.presenters;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.fs.common.IPresenter;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.presenters.IColumnFragmentView
 */
public interface IColumnFragmentPresenter extends IPresenter {

    void restoreState(Bundle restore);
    void storeState(Bundle store);

    RadioGroup.OnCheckedChangeListener provideCheckedChangeListener();
    Spinner.OnItemSelectedListener     provideItemSelectedListener();

    String[] readArray(@StringRes int resId);
}