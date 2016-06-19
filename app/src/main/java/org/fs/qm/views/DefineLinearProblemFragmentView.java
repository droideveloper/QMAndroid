package org.fs.qm.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractFragment;
import org.fs.qm.R;
import org.fs.qm.adapters.GridRecyclerAdapter;
import org.fs.qm.components.ActivityComponent;
import org.fs.qm.entities.Objective;
import org.fs.qm.presenters.DefineLinearProblemFragmentPresenter;
import org.fs.qm.presenters.IDefineLinearProblemFragmentPresenter;
import org.fs.qm.widget.RecyclerView;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.views.DefineLinearProblemFragmentView
 */
public class DefineLinearProblemFragmentView extends AbstractFragment<IDefineLinearProblemFragmentPresenter> implements IDefineLinearProblemFragmentView {


    public static DefineLinearProblemFragmentView newInstance(Objective obj, int rowSize, int colSize, String rowTemplate, String colTemplate) {
        Bundle args = new Bundle();
        args.putString(DefineLinearProblemFragmentPresenter.ARGS_OBJECTIVE, obj.name());
        args.putInt(DefineLinearProblemFragmentPresenter.ARGS_ROW_COUNT, rowSize);
        args.putInt(DefineLinearProblemFragmentPresenter.ARGS_COL_COUNT, colSize);
        args.putString(DefineLinearProblemFragmentPresenter.ARGS_ROW_NTEMPLATE, rowTemplate);
        args.putString(DefineLinearProblemFragmentPresenter.ARGS_COL_NTEMPLATE, colTemplate);

        DefineLinearProblemFragmentView fragment = new DefineLinearProblemFragmentView();
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onBindViews(inflater.inflate(R.layout.layout_define_lp, container, false));
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.restoreState(savedInstanceState != null ? savedInstanceState : getArguments());
        presenter.onCreate();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.storeState(outState);
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

    @Override public View onBindViews(View view) {
        recyclerView = ViewUtility.castAsField(view);
        return view;
    }

    @Override public void bindAdapter(GridRecyclerAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
    }

    @Override public FragmentManager provideFragmentManager() {
       return getChildFragmentManager();
    }

    @Override public boolean isAvailable() {
        return isCallingSafe();
    }

    @Override
    public ActivityComponent getActivityComponent() {
        //todo implement
        return null;
    }

    @Override protected IDefineLinearProblemFragmentPresenter presenter() {
        return new DefineLinearProblemFragmentPresenter(this);
    }

    @Override protected String getClassTag() {
        return DefineLinearProblemFragmentView.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}