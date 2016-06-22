package org.fs.qm.presenters;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.common.SimpleTextWatcher;
import org.fs.qm.views.ITextDialogFragmentView;
import org.fs.util.StringUtility;

/**
 * Created by Fatih on 19/06/16.
 * as org.fs.qm.presenters.TextCellPresenter
 */
public class TextDialogFragmentPresenter extends AbstractPresenter<ITextDialogFragmentView> implements ITextDialogFragmentPresenter, View.OnClickListener {


    private final SimpleTextWatcher textWatcher = new SimpleTextWatcher() {

        private final static String defaultText = "0.0";

        @Override public void afterTextChanged(Editable s) {
            final String txt = s.toString();
            if(view.isAvailable()) {
                if (StringUtility.isNullOrEmpty(txt)) {
                    view.showError("we set default text, please set your value");
                } else {
                    view.hideError();
                }
            }
            selectedText = StringUtility.isNullOrEmpty(txt) ? defaultText : txt;
        }
    };

    private String                                            selectedText;
    private ITextDialogFragmentView.OnTextViewChangedListener listener;

    public TextDialogFragmentPresenter(ITextDialogFragmentView view) {
        super(view);
    }

    @Override protected String getClassTag() {
        return TextDialogFragmentPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public void setOnTextChangedListener(ITextDialogFragmentView.OnTextViewChangedListener listener) {
        this.listener = listener;
    }

    @Override public View.OnClickListener provideViewClickListener() {
        return this;
    }

    @Override public void onClick(View v) {
        boolean isPositive = view.isSelectedPositive(v);
        if(isPositive) {
            if(listener != null) {
                listener.onTextChanged(selectedText);
            }
        }
        view.close();
    }

    @Override public TextWatcher provideTextWatcher() {
        return textWatcher;
    }

    @Override public void onCreate() {
        //ignored
    }

    @Override public void onStart() {
        if(view.isAvailable()) {
            Dialog dialog = view.getDialog();
            if(dialog != null) {
                final Window window = dialog.getWindow();
                if(window != null) {
                    //if dialog needs to set full screen with With match parent
                    //this is the solution
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                                     ViewGroup.LayoutParams.WRAP_CONTENT);
                    window.getAttributes().flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                }
            }
        }
    }

    @Override public void onStop() {
        //ignored
    }

    @Override public void onDestroy() {
        //ignored
    }
}