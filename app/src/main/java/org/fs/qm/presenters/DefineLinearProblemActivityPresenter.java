package org.fs.qm.presenters;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.fs.common.AbstractPresenter;
import org.fs.qm.entities.Objective;
import org.fs.qm.views.DefineLinearProblemFragmentView;
import org.fs.qm.views.IDefineLinearProblemActivityView;

/**
 * Created by Fatih on 22/06/16.
 * as org.fs.qm.presenters.DefineLinearProblemActivityPresenter
 */
public class DefineLinearProblemActivityPresenter extends AbstractPresenter<IDefineLinearProblemActivityView> implements IDefineLinearProblemActivityPresenter,
                                                                                                                         AppBarLayout.OnOffsetChangedListener {

    private Objective  objective;
    private int        rowSize;
    private int        colSize;
    private String     rowName;
    private String     colName;
    private String     title;

    private float      density;
    private int        toolbarHeight;
    private int        layoutHeight     = 180;

    public DefineLinearProblemActivityPresenter(IDefineLinearProblemActivityView view) {
        super(view);
    }

    @Override public void restoreState(Bundle restore) {
        if (restore != null) {
            String name = restore.getString(DefineLinearProblemFragmentPresenter.ARGS_OBJECTIVE);
            if(name != null) {
                objective = Objective.valueOf(name);
            }
            rowSize = restore.getInt(DefineLinearProblemFragmentPresenter.ARGS_ROW_COUNT);
            colSize = restore.getInt(DefineLinearProblemFragmentPresenter.ARGS_COL_COUNT);
            rowName = restore.getString(DefineLinearProblemFragmentPresenter.ARGS_ROW_NTEMPLATE);
            colName = restore.getString(DefineLinearProblemFragmentPresenter.ARGS_COL_NTEMPLATE);
            title   = restore.getString("linear.problem.title");
        }
    }

    @Override public void storeState(Bundle store) {
        store.putString(DefineLinearProblemFragmentPresenter.ARGS_OBJECTIVE,        objective.name());
        store.putInt(DefineLinearProblemFragmentPresenter.ARGS_ROW_COUNT,           rowSize);
        store.putInt(DefineLinearProblemFragmentPresenter.ARGS_COL_COUNT,           colSize);
        store.putString(DefineLinearProblemFragmentPresenter.ARGS_ROW_NTEMPLATE,    rowName);
        store.putString(DefineLinearProblemFragmentPresenter.ARGS_COL_NTEMPLATE,    colName);
        store.putString("linear.problem.title", title);
    }

    @Override public void onBackPressed() {
        view.finish();
    }

    @Override public Toolbar.OnClickListener navigationListener() {
        return new Toolbar.OnClickListener(){
            @Override public void onClick(View clickedView) {
                view.finish();
            }
        };
    }

    @Override public View.OnClickListener clickListener() {
        return new View.OnClickListener() {
            @Override public void onClick(View clickedView) {
                //do next operation TODO solve
            }
        };
    }

    @Override public AppBarLayout.OnOffsetChangedListener offsetChangeListener() {
        return this;
    }

    @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        final int collapsedOffset = -Math.round(density * layoutHeight) + (toolbarHeight = toolbarHeight != 0 ? toolbarHeight : view.toolBarHeight());
        if(verticalOffset == collapsedOffset) {
            view.setContentTitle(title);
        } else {
            //todo change this to problem string
            view.setExpandedTitle(title);
        }
    }

    @Override public void onCreate() {
        view.onBindView();
        density = view.density();
    }

    @Override public void onStart() {
        if(view.doesNeedNewCommit()) {
            DefineLinearProblemFragmentView frag = DefineLinearProblemFragmentView.newInstance(objective, rowSize, colSize, rowName, colName);
            view.commit(frag);
        }
    }

    @Override public void onStop() {
        //ignored
    }

    @Override public void onDestroy() {
        //ignored
    }

    @Override protected String getClassTag() {
        return DefineLinearProblemActivityPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return Boolean.TRUE;
    }
}