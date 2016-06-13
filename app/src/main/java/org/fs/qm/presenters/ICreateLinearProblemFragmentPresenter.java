package org.fs.qm.presenters;

import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.SeekBar;

import org.fs.common.IPresenter;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.presenters.ICreateLinearProblemPresenter
 */
public interface ICreateLinearProblemFragmentPresenter extends IPresenter {

    void restoreState(Bundle input);
    void storeState(Bundle output);

    String      getDefaultTitle();
    String      getDefaultNumberOfRow();
    String      getDefaultNumberOfColumn();

    TextWatcher provideTitleWatcher();
    TextWatcher provideRowWatcher();
    TextWatcher provideColumnWatcher();

    SeekBar.OnSeekBarChangeListener provideRowChangeListener();
    SeekBar.OnSeekBarChangeListener provideColumnChangeListener();
}