package org.fs.qm.presenters;

import android.os.Bundle;

import org.fs.common.AbstractPresenter;
import org.fs.common.BusManager;
import org.fs.core.AbstractApplication;
import org.fs.qm.adapters.GridRecyclerAdapter;
import org.fs.qm.entities.BoundCell;
import org.fs.qm.entities.EmptyCell;
import org.fs.qm.entities.ICellEntity;
import org.fs.qm.entities.LabelCell;
import org.fs.qm.entities.Objective;
import org.fs.qm.entities.TextCell;
import org.fs.qm.views.IDefineLinearProblemFragmentView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

        }
    }

    @Override public void storeState(Bundle store) {
        store.putString(ARGS_OBJECTIVE, objective.name());
        store.putInt(ARGS_ROW_COUNT, rowSize);
        store.putInt(ARGS_COL_COUNT, colSize);
        store.putString(ARGS_ROW_NTEMPLATE, rowName);
        store.putString(ARGS_COL_NTEMPLATE, colName);

    }

    @Override public void onBackPressed() {

    }

    private final static int INDEX_HEADER = 0x0;
    private final static int INDEX_DATA   = 0x1;

    @Override public void onCreate() {
        if(problem == null) {
            problem = new ArrayList<>(rowSize);
            for (int i = 0; i < rowSize; i++) {
                List<ICellEntity> col = new ArrayList<>(colSize);
                for (int j = 0; j < colSize; j++) {
                    if (i == INDEX_HEADER) {
                        if(j == 0) {
                            //empty
                            col.add(EmptyCell.newEmptyCellEntity());
                        } else if(j < colSize - 2) {
                            //variable name
                            col.add(LabelCell.newLabelCellEntity(String.format(Locale.getDefault(), colName, j)));
                        } else if(j == colSize - 2) {
                            //sign name, empty
                            col.add(EmptyCell.newEmptyCellEntity());
                        } else if (j == colSize - 1) {
                            //rhs name
                            col.add(LabelCell.newLabelCellEntity("RHS"));
                        }
                    } else {
                        //Objective Function Definition
                        if(i == INDEX_DATA) {
                            if(j == 0) {
                                //objective type
                                col.add(LabelCell.newLabelCellEntity(objective.name()));
                            } else if(j < colSize - 2) {
                                //it's definitions
                                col.add(TextCell.newTextCellEntity(0.0));
                            } else if (j == colSize - 2) {
                                col.add(EmptyCell.newEmptyCellEntity());
                            } else if (j == colSize -1) {
                                col.add(EmptyCell.newEmptyCellEntity());
                            }
                        }
                        //Constraint Function Definition
                        else {
                            if(j == 0) {
                                //constraint label
                                col.add(LabelCell.newLabelCellEntity(String.format(Locale.getDefault(), rowName, i)));
                            } else if(j < colSize - 2) {
                                //until end its our fields
                                col.add(TextCell.newTextCellEntity(0.0));
                            } else if (j == colSize - 2) {
                                //signs
                                col.add(BoundCell.newBoundCellEntity(Arrays.asList(">=",">", "<=", "<", "=")));
                            } else if (j == colSize -1) {
                                //constraint rhs
                                col.add(TextCell.newTextCellEntity(0.0));
                            }
                        }
                    }
                }
                problem.add(col);
            }
        }
    }

    @Override public void onStart() {
        if(view.isAvailable()) {
            BusManager busManager = new BusManager();
            GridRecyclerAdapter adapter = new GridRecyclerAdapter(problem, view.getContext(), busManager);
            view.bindAdapter(adapter);
        }
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