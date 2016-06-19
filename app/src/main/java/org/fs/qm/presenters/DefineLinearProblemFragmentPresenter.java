package org.fs.qm.presenters;

import android.os.Bundle;
import android.util.Log;

import org.fs.common.AbstractPresenter;
import org.fs.core.AbstractApplication;
import org.fs.qm.adapters.GridRecyclerAdapter;
import org.fs.qm.entities.ICellEntity;
import org.fs.qm.entities.Objective;
import org.fs.qm.usecases.ILinearProblemUseCase;
import org.fs.qm.usecases.LinearProblemUseCase;
import org.fs.qm.views.IDefineLinearProblemFragmentView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.presenters.DefineLinearProblemFragmentPresenter
 */
public class DefineLinearProblemFragmentPresenter extends AbstractPresenter<IDefineLinearProblemFragmentView> implements IDefineLinearProblemFragmentPresenter,
                                                                                                                         ILinearProblemUseCase.OnLinearProblemUseCaseListener {

    public final static String ARGS_OBJECTIVE       = "linear.problem.objective";
    public final static String ARGS_ROW_COUNT       = "linear.problem.row.count";
    public final static String ARGS_COL_COUNT       = "linear.problem.col.count";
    public final static String ARGS_ROW_NTEMPLATE   = "linear.problem.row.name.template";
    public final static String ARGS_COL_NTEMPLATE   = "linear.problem.col.name.template";

    //how can I store this problem in state ?
    private final static String ARGS_PROBLEM        = "linear.problem.data";

    private Objective                    objective;
    private int                          rowSize;
    private int                          colSize;
    private String                       rowName;
    private String                       colName;
    private List<List<ICellEntity>>      problem;

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
            //we gotta do it this way
            if(restore.containsKey("size")) {
                //read size
                int size = restore.getInt("size");
                problem = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    //read index
                    int index = restore.getInt(String.valueOf(i));
                    //read object at index
                    List<ICellEntity> item = restore.getParcelableArrayList("itemAt" + index);
                    //put in position
                    problem.add(index, item);
                }
            }
        }
    }

    @Override public void storeState(Bundle store) {
        store.putString(ARGS_OBJECTIVE, objective.name());
        store.putInt(ARGS_ROW_COUNT, rowSize);
        store.putInt(ARGS_COL_COUNT, colSize);
        store.putString(ARGS_ROW_NTEMPLATE, rowName);
        store.putString(ARGS_COL_NTEMPLATE, colName);
        if(problem != null && !problem.isEmpty()) {
            //write size
            store.putInt("size", problem.size());
            for (List<ICellEntity> item : problem) {
                int index = problem.indexOf(item);
                //write index
                store.putInt(String.valueOf(index), index);
                //write object at index
                store.putParcelableArrayList("itemAt" + index, new ArrayList<>(item));
            }
        }
    }

    @Override public void onBackPressed() {
        //ignored //todo
    }

    @Override public void sucess(List<List<ICellEntity>> dataSet) {
        this.problem = dataSet;
        onStart();//we can do this it here
    }

    @Override public void error(Throwable exp) {
        if(exp != null) {
            StringWriter stringWriter = new StringWriter(128);
            PrintWriter printWriter = new PrintWriter(stringWriter);
            exp.printStackTrace(printWriter);
            log(Log.INFO, stringWriter.toString());
        }
    }

    @Override public void onCreate() {
        if(problem == null) {
            LinearProblemUseCase useCase = new LinearProblemUseCase(view.getContext());
            //set data
            useCase.setObjective(objective);
            useCase.setRowCount(rowSize);
            useCase.setColCount(colSize);
            useCase.setColName(colName);
            useCase.setRowName(rowName);
            //listener
            useCase.setListener(this);
            //execute in parallel
            useCase.executeAsync();
        }
    }

    @Override public void onStart() {
        if(view.isAvailable() && problem != null) {
            GridRecyclerAdapter adapter = new GridRecyclerAdapter(problem, view.getContext());
            view.bindAdapter(adapter);
        }
    }

    @Override public void onStop() {
        //ignored
    }

    @Override public void onDestroy() {
        //ignored
    }

    @Override protected String getClassTag() {
        return DefineLinearProblemFragmentPresenter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

}