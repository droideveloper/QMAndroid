package org.fs.qm.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractFragment;
import org.fs.qm.R;
import org.fs.qm.adapters.MonthsAdapter;
import org.fs.qm.presenters.IRowFragmentPresenter;
import org.fs.qm.presenters.RowFragmentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.views.RowFragmentView
 */
public class RowFragmentView extends AbstractFragment<IRowFragmentPresenter> implements IRowFragmentView {

    @BindView(value = R.id.rgRows) RadioGroup           rgRows;
    @BindView(value = R.id.spMonth) Spinner             spMonth;

    private MonthsAdapter adapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_row_names, container, false);
        ButterKnife.bind(view);
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

    @Override public void enableMonth() {
        spMonth.setEnabled(true);
    }

    @Override public void disableMonth() {
        spMonth.setEnabled(false);
    }

    @Override public boolean isAvailable() {
        return isCallingSafe();
    }

    @Override public Context getContext() {
        return super.getContext();
    }

    @Override public void onSetupViews() {
        rgRows.setOnCheckedChangeListener(presenter.provideCheckedChangeListener());
        adapter = new MonthsAdapter(getContext());
        spMonth.setAdapter(adapter);
        spMonth.setOnItemSelectedListener(presenter.provideItemSelectedListener());
    }

    @Override public void setData(List<String> dataSet) {
        adapter.addAll(dataSet);
    }

    @Override protected IRowFragmentPresenter presenter() {
        return new RowFragmentPresenter(this);
    }

    @Override protected String getClassTag() {
        return RowFragmentView.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}