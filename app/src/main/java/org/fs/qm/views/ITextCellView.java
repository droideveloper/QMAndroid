package org.fs.qm.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import org.fs.common.IView;

/**
 * Created by Fatih on 19/06/16.
 * as org.fs.qm.views.ITextCellView
 */
public interface ITextCellView extends IView {

    View onBindView(View view);
    void close();

    void showError(String error);
    void hideError();

    void setTextChangedListener(OnTextViewChangedListener listener);

    Context getContext();
    boolean isSelectedPositive(View view);
    boolean isAvailable();
    Dialog getDialog();

    interface OnTextViewChangedListener {
        void onTextChanged(String nextStr);
    }
}