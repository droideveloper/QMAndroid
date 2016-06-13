package org.fs.qm.presenters;

import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.SeekBar;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.R;
import org.fs.qm.common.SimpleTextWatcher;
import org.fs.qm.views.ICreateLinearProblemFragmentView;
import org.fs.util.StringUtility;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.presenters.CreateLinearProblemFragmentPresenter
 */
public class CreateLinearProblemFragmentPresenter extends AbstractPresenter<ICreateLinearProblemFragmentView> implements ICreateLinearProblemFragmentPresenter {

    /**
     * TitleTextWatcher implementation
     */
    private SimpleTextWatcher titleTextWatcher = new SimpleTextWatcher() {

        final String defaultTitle = getDefaultTitle();

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            final String str = s.toString();
            if(str.startsWith(defaultTitle)) {
                if(view.isAvailable()) {
                    view.setTitle(str);
                }
            } else {
                if (str.length() == 0) {
                    if(view.isAvailable()) {
                        view.setTitle(defaultTitle);
                    }
                }
            }
        }
    };

    /**
     * RowTextWatcher implementation
     */
    private SimpleTextWatcher rowTextWatcher = new SimpleTextWatcher() {

        final String defaultRowCountStr = getDefaultNumberOfRow();
        final int    defaultRowCountInt = Integer.parseInt(defaultRowCountStr);

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            final String str = s.toString();
            if(StringUtility.isNullOrEmpty(str)) {
                if(view.isAvailable()) {
                    view.setRowCount(defaultRowCountStr);
                    view.setRowCount(defaultRowCountInt);
                }
            } else {
                final int progress = Integer.parseInt(str);
                if(view.isAvailable()) {
                    view.setRowCount(progress);
                }
            }
        }
    };

    /**
     * ColumnTextWatcher
     */
    private SimpleTextWatcher columnTextWatcher = new SimpleTextWatcher() {

        final String defaultColumnCountStr = getDefaultNumberOfColumn();
        final int    defaultColumnCountInt = Integer.parseInt(defaultColumnCountStr);

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            final String str = s.toString();
            if(StringUtility.isNullOrEmpty(str)) {
                if(view.isAvailable()) {
                    view.setColumnCount(defaultColumnCountStr);
                    view.setColumnCount(defaultColumnCountInt);
                }
            } else {
                final int progress = Integer.parseInt(str);
                if(view.isAvailable()) {
                    view.setColumnCount(progress);
                }
            }
        }
    };

    public CreateLinearProblemFragmentPresenter(ICreateLinearProblemFragmentView view) {
        super(view);
    }

    @Override
    public void restoreState(Bundle input) {

    }

    @Override
    public void storeState(Bundle output) {

    }

    @Override public TextWatcher provideTitleWatcher() {
        return titleTextWatcher;
    }

    @Override public TextWatcher provideRowWatcher() {
        return rowTextWatcher;
    }

    @Override public TextWatcher provideColumnWatcher() {
        return columnTextWatcher;
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

    @Override public String getDefaultTitle() {
        return view.getContext().getString(R.string.default_title);
    }

    @Override public String getDefaultNumberOfColumn() {
        return view.getContext().getString(R.string.default_num_of_cols);
    }

    @Override public String getDefaultNumberOfRow() {
        return view.getContext().getString(R.string.default_num_of_rows);
    }

    @Override protected String getClassTag() {
        return CreateLinearProblemFragmentPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}