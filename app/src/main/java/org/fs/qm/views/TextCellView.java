package org.fs.qm.views;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractDialogFragment;
import org.fs.qm.R;
import org.fs.qm.presenters.ITextCellPresenter;
import org.fs.qm.presenters.TextCellPresenter;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 19/06/16.
 * as org.fs.qm.views.TextCellView
 */
public class TextCellView extends AbstractDialogFragment<ITextCellPresenter> implements ITextCellView {

    TextInputLayout txtInputLayout;
    EditText        txtInput;
    Button          btnOk;
    Button          btnCancel;

    public TextCellView() {
        //use default theme
        super();//if I do not call this it will mess my code
        setStyle(STYLE_NO_TITLE, getTheme());
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onBindView(inflater.inflate(R.layout.layout_enter_coef, container, false));
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onCreate();
    }

    @Override public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override protected String getClassTag() {
        return TextCellView.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override protected ITextCellPresenter presenter() {
        return new TextCellPresenter(this);
    }

    @Override public boolean isSelectedPositive(View view) {
        return view != null && btnOk.equals(view);
    }

    @Override public View onBindView(View view) {
        txtInputLayout = ViewUtility.findViewById(view, R.id.txtInputLayout);
        txtInput = ViewUtility.findViewById(view, R.id.txtInput);
        txtInput.addTextChangedListener(presenter.provideTextWatcher());

        btnOk = ViewUtility.findViewById(view, R.id.btnOk);
        btnOk.setOnClickListener(presenter.provideViewClickListener());
        btnCancel = ViewUtility.findViewById(view, R.id.btnCancel);
        btnCancel.setOnClickListener(presenter.provideViewClickListener());
        return view;
    }

    @Override public Dialog getDialog() {
        return super.getDialog();
    }

    @Override public boolean isAvailable() {
        return isCallingSafe();
    }

    @Override public void setTextChangedListener(OnTextViewChangedListener listener) {
        presenter.setOnTextChangedListener(listener);
    }

    @Override public void close() {
        dismiss();
    }

    @Override public void showError(String error) {
        if(!txtInputLayout.isErrorEnabled()) {
            txtInputLayout.setErrorEnabled(true);
        }
        txtInputLayout.setError(error);
    }

    @Override public void hideError() {
        if(txtInputLayout.isErrorEnabled()) {
            txtInputLayout.setErrorEnabled(false);
        }
        txtInputLayout.setError(null);
    }
}