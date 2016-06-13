package org.fs.qm.presenters;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.R;
import org.fs.qm.entities.Objective;
import org.fs.qm.common.SimpleSeekBarChangeListener;
import org.fs.qm.common.SimpleTextWatcher;
import org.fs.qm.views.ICreateLinearProblemFragmentView;
import org.fs.util.StringUtility;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.presenters.CreateLinearProblemFragmentPresenter
 */
public class CreateLinearProblemFragmentPresenter extends AbstractPresenter<ICreateLinearProblemFragmentView> implements ICreateLinearProblemFragmentPresenter,
                                                                                                                         RadioGroup.OnCheckedChangeListener {

    /**
     * TitleTextWatcher implementation
     */
    private SimpleTextWatcher titleTextWatcher = new SimpleTextWatcher() {

        @Override public void afterTextChanged(Editable s) {
            final String defaultTitle = getDefaultTitle();
            final String str = s.toString();
            //if we only have default title we do not want to replace it
            boolean isNotSame = !defaultTitle.equalsIgnoreCase(str);
            if(str.startsWith(defaultTitle) && isNotSame) {
                if(view.isAvailable()) {
                    view.setTitle(str.replace(defaultTitle, ""));
                }
            } else {
                if (str.length() == 0) {
                    if(view.isAvailable()) {
                        view.setTitle(defaultTitle);
                    }
                }
            }
            //set title here well, might not want to but this is tracking change
            if(view.isAvailable()) {
                view.setParentTitle(str.startsWith(defaultTitle) ? str.replace(defaultTitle, "") : str);
            }
        }
    };

    /**
     * RowTextWatcher implementation
     */
    private SimpleTextWatcher rowTextWatcher = new SimpleTextWatcher() {

        @Override public void afterTextChanged(Editable s) {
            final String defaultRowCountStr = getDefaultNumberOfRow();
            final int    defaultRowCountInt = Integer.parseInt(defaultRowCountStr);
            final String str = s.toString();
            if(StringUtility.isNullOrEmpty(str)) {
                if(view.isAvailable()) {
                    view.setRowCount(defaultRowCountStr);
                    view.setRowCount(defaultRowCountInt);
                }
            } else {
                final int progress = Integer.parseInt(str);
                if(view.isAvailable()) {
                    if(progress >= defaultRowCountInt) {
                        view.setRowCount(progress);
                    } else {
                        view.setRowCount(defaultRowCountInt);
                        view.setRowCount(defaultRowCountStr);
                    }
                }
            }
        }
    };

    /**
     * ColumnTextWatcher
     */
    private SimpleTextWatcher columnTextWatcher = new SimpleTextWatcher() {

        @Override public void afterTextChanged(Editable s) {
            final String defaultColumnCountStr = getDefaultNumberOfColumn();
            final int    defaultColumnCountInt = Integer.parseInt(defaultColumnCountStr);
            final String str = s.toString();
            if(StringUtility.isNullOrEmpty(str)) {
                if(view.isAvailable()) {
                    view.setColumnCount(defaultColumnCountStr);
                    view.setColumnCount(defaultColumnCountInt);
                }
            } else {
                final int progress = Integer.parseInt(str);
                if(view.isAvailable()) {
                    if(progress >= defaultColumnCountInt) {
                        view.setColumnCount(progress);
                    } else {
                        view.setColumnCount(defaultColumnCountStr);
                        view.setColumnCount(defaultColumnCountInt);
                    }
                }
            }
        }
    };

    /**
     * RowSeekBarChangeListener
     */
    private SimpleSeekBarChangeListener rowChangeListener = new SimpleSeekBarChangeListener() {

        @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            final int defaultRowCountInt = Integer.parseInt(getDefaultNumberOfRow());
            if(fromUser) {
                if(view.isAvailable()) {
                    if (progress >= defaultRowCountInt) {
                        view.setRowCount(String.valueOf(progress));
                    } else {
                        view.setRowCount(String.valueOf(defaultRowCountInt));
                        view.setRowCount(defaultRowCountInt);
                    }
                }
            }
        }
    };

    /**
     * ColumnSeekBarChangeListener
     */
    private SimpleSeekBarChangeListener columnChangeListener = new SimpleSeekBarChangeListener() {

        @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            final int defaultColumnCountInt = Integer.parseInt(getDefaultNumberOfColumn());
            if(fromUser) {
                if(view.isAvailable()) {
                    if(progress >= defaultColumnCountInt) {
                        view.setColumnCount(String.valueOf(progress));
                    } else {
                        view.setColumnCount(String.valueOf(progress));
                        view.setColumnCount(progress);
                    }
                }
            }
        }
    };

    private Objective selectedType;

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

    @Override public SeekBar.OnSeekBarChangeListener provideRowChangeListener() {
        return rowChangeListener;
    }

    @Override public SeekBar.OnSeekBarChangeListener provideColumnChangeListener() {
        return columnChangeListener;
    }

    @Override public RadioGroup.OnCheckedChangeListener provideTypeChangeListener() {
        return this;
    }

    @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
        selectedType = Objective.from(checkedId);
        log(Log.ERROR, String.valueOf(selectedType));
    }

    @Override public void onCreate() {
        if(view.isAvailable()) {
            view.setUpViews();
            selectedType = Objective.from(view.getType());
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

    @Override public Objective getSelectedType() {
        return selectedType;
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