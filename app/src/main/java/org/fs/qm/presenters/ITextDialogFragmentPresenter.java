package org.fs.qm.presenters;

import android.text.TextWatcher;
import android.view.View;

import org.fs.common.IPresenter;
import org.fs.qm.views.ITextDialogFragmentView;

/**
 * Created by Fatih on 19/06/16.
 * as org.fs.qm.presenters.ITextCellPresenter
 */
public interface ITextDialogFragmentPresenter extends IPresenter {

    void setOnTextChangedListener(ITextDialogFragmentView.OnTextViewChangedListener listener);

    TextWatcher             provideTextWatcher();
    View.OnClickListener    provideViewClickListener();
}