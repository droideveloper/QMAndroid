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
import org.fs.qm.presenters.ColumnFragmentPresenter;
import org.fs.qm.presenters.IColumnFragmentPresenter;
import org.fs.util.ViewUtility;

import java.util.List;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.views.ColumnFragmentView
 */
public class ColumnFragmentView extends AbstractFragment<IColumnFragmentPresenter> implements IColumnFragmentView {

    private   RadioGroup rgColumns;
    private   Spinner    spMonth;

    private MonthsAdapter adapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_column_names, container, false);
        rgColumns = ViewUtility.castAsField(view);
        spMonth   = ViewUtility.findViewById(view, R.id.spMonth);
        return view;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.restoreState(savedInstanceState != null ? savedInstanceState : getArguments());
        presenter.onCreate();

//        FragmentTransaction trans = getChildFragmentManager().beginTransaction();
//        trans.setCustomAnimations(R.anim.translate_in,      //enter
//                                  R.anim.scale_out,         //exit
//                                  R.anim.scale_in,          //popEnter
//                                  R.anim.translate_out);    //popExit
//        trans.commit();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.storeState(outState);
    }

    @Override public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override public void onSetupViews() {
        rgColumns.setOnCheckedChangeListener(presenter.provideCheckedChangeListener());
        adapter = new MonthsAdapter(getContext());
        spMonth.setAdapter(adapter);
        spMonth.setOnItemSelectedListener(presenter.provideItemSelectedListener());
    }

    @Override public void setData(List<String> dataSet) {
        adapter.addAll(dataSet);
    }

    @Override public void disableMonth() {
        spMonth.setEnabled(false);
    }

    @Override public void enableMonth() {
        spMonth.setEnabled(true);
    }

    @Override public Context getContext() {
        return super.getContext();
    }

    @Override protected IColumnFragmentPresenter presenter() {
        return new ColumnFragmentPresenter(this);
    }

    @Override public boolean isAvailable() {
        return isCallingSafe();
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
        return ColumnFragmentView.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}