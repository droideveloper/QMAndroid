package org.fs.qm.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractFragment;
import org.fs.qm.R;
import org.fs.qm.adapters.AssignStateAdapter;
import org.fs.qm.common.ReverseDepthPageTransformer;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.components.ApplicationComponent;
import org.fs.qm.presenters.CreateLinearProblemFragmentPresenter;
import org.fs.qm.presenters.ICreateLinearProblemFragmentPresenter;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.views.CreateLinearProblemFragmentView
 */
public class CreateLinearProblemFragmentView extends AbstractFragment<ICreateLinearProblemFragmentPresenter> implements ICreateLinearProblemFragmentView {

    private EditText        titleEditText;
    private RadioGroup      typeRadioGroup;
    private EditText        rowCountEditText;
    private SeekBar         rowCountSeekBar;
    private EditText        columnCountEditText;
    private SeekBar         columnCountSeekBar;
    private ViewPager       namesViewPager;


    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onBindView(inflater.inflate(R.layout.layout_create_lp, container, false));
    }

    @Override public View onBindView(View view) {
        titleEditText       = ViewUtility.findViewById(view, R.id.edtTitle);
        typeRadioGroup      = ViewUtility.findViewById(view, R.id.rgType);
        rowCountEditText    = ViewUtility.findViewById(view, R.id.edtNumberOfRows);
        rowCountSeekBar     = ViewUtility.findViewById(view, R.id.seekNumberOfRows);
        columnCountEditText = ViewUtility.findViewById(view, R.id.edtNumberOfCols);
        columnCountSeekBar  = ViewUtility.findViewById(view, R.id.seekNumberOfCols);
        namesViewPager      = ViewUtility.findViewById(view, R.id.vpAssign);
        return view;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.restoreState(savedInstanceState != null ? savedInstanceState : getArguments());
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

    @Override
    public void setUpViews() {
        //add watchers
        titleEditText.addTextChangedListener(presenter.provideTitleWatcher());
        rowCountEditText.addTextChangedListener(presenter.provideRowWatcher());
        columnCountEditText.addTextChangedListener(presenter.provideColumnWatcher());

        typeRadioGroup.setOnCheckedChangeListener(presenter.provideTypeChangeListener());

        //add change listeners
        rowCountSeekBar.setOnSeekBarChangeListener(presenter.provideRowChangeListener());
        columnCountSeekBar.setOnSeekBarChangeListener(presenter.provideColumnChangeListener());

        //load adapter
        AssignStateAdapter adapter = new AssignStateAdapter(getContext(), getChildFragmentManager());
        namesViewPager.setAdapter(adapter);
        namesViewPager.setPageTransformer(false, new ReverseDepthPageTransformer());//this is nice transformer
    }

    @Override public void setTitle(String title) {
        titleEditText.setText(title);
        titleEditText.setSelection(title.length());
    }

    @Override public void setParentTitle(String title) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if(isCallingSafe()) {
            appCompatActivity.setTitle(title);
        }
    }

    //    @Override
//    public void setType(int type) {
//
//    }

    @Override public void setRowCount(int progress) {
        rowCountSeekBar.setProgress(progress);
    }

    @Override public void setRowCount(String progress) {
        rowCountEditText.setText(progress);
    }

    @Override public void setColumnCount(int progress) {
        columnCountSeekBar.setProgress(progress);
    }

    @Override public void setColumnCount(String progress) {
        columnCountEditText.setText(progress);
    }

    @Override public String getTitle() {
        return titleEditText.getText().toString();
    }

    @Override public int getType() {
        return typeRadioGroup.getCheckedRadioButtonId();
    }

    @Override public int getRowCount() {
        return rowCountSeekBar.getProgress();
    }

    @Override public int getColumnCount() {
        return columnCountSeekBar.getProgress();
    }

//    @Override
//    public String getRowName() {
//        return null;
//    }
//
//    @Override
//    public String getColumnName() {
//        return null;
//    }

    @Override public Context getContext() {
        return super.getContext();
    }

    @Override public boolean isAvailable() {
        return isCallingSafe();
    }

    @Override protected ICreateLinearProblemFragmentPresenter presenter() {
        return new CreateLinearProblemFragmentPresenter(this);
    }

    @Override
    public ApplicationComponent getApplicationComponent() {
        //todo implement
        return null;
    }

    @Override
    public ActivityComponent getActivityComponent() {
        //todo implement
        return null;
    }

    @Override protected String getClassTag() {
        return CreateLinearProblemFragmentView.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}