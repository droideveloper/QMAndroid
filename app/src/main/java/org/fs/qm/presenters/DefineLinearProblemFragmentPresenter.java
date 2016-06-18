package org.fs.qm.presenters;

import android.os.Bundle;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.entities.Objective;
import org.fs.qm.views.IDefineLinearProblemFragmentView;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.presenters.DefineLinearProblemFragmentPresenter
 */
public class DefineLinearProblemFragmentPresenter extends AbstractPresenter<IDefineLinearProblemFragmentView> implements IDefineLinearProblemFragmentPresenter {

    public final static String ARGS_OBJECTIVE       = "linear.problem.objective";
    public final static String ARGS_ROW_COUNT       = "linear.problem.row.count";
    public final static String ARGS_COL_COUNT       = "linear.problem.col.count";
    public final static String ARGS_ROW_NTEMPLATE   = "linear.problem.row.name.template";
    public final static String ARGS_COL_NTEMPLATE   = "linear.problem.col.name.template";

    private Objective objective;
    private int       rowSize;
    private int       colSize;
    private String    rowName;
    private String    colName;

    public DefineLinearProblemFragmentPresenter(IDefineLinearProblemFragmentView view) {
        super(view);
    }

    @Override public void restoreState(Bundle restore) {
        if(restore != null) {
            String name = restore.getString(ARGS_OBJECTIVE);
            if(name != null) {
                objective = Objective.valueOf(name);
            }
            rowSize = restore.getInt(ARGS_ROW_COUNT);
            colSize = restore.getInt(ARGS_COL_COUNT);
            rowName = restore.getString(ARGS_ROW_NTEMPLATE);
            colName = restore.getString(ARGS_COL_NTEMPLATE);
        }
    }

    @Override public void storeState(Bundle store) {
        store.putString(ARGS_OBJECTIVE, objective.name());
        store.putInt(ARGS_ROW_COUNT, rowSize);
        store.putInt(ARGS_COL_COUNT, colSize);
        store.putString(ARGS_ROW_NTEMPLATE, rowName);
        store.putString(ARGS_COL_NTEMPLATE, colName);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override public void onStop() {
        //no op
    }

    @Override public void onDestroy() {
        //no op
    }

    @Override protected String getClassTag() {
        return DefineLinearProblemFragmentPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}