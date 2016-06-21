package org.fs.qm.usecases;

import android.content.Context;
import android.util.Log;

import org.fs.core.AbstractApplication;
import org.fs.qm.R;
import org.fs.qm.entities.BoundCell;
import org.fs.qm.entities.EmptyCell;
import org.fs.qm.entities.ICellEntity;
import org.fs.qm.entities.LabelCell;
import org.fs.qm.entities.Objective;
import org.fs.qm.entities.TextCell;
import org.fs.util.Collections;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Fatih on 19/06/16.
 * as org.fs.qm.usecases.LinearProblemUseCase
 */
public class LinearProblemUseCase implements ILinearProblemUseCase {

    private final static int INDEX_ROW_LABEL     = 0x0;
    private final static int INDEX_ROW_OBJECTIVE = 0x1;

    private static final int INDEX_COLM_START    = 0x0;
    private static int INDEX_COLM_DATA;
    private static int INDEX_COLM_LAST;

    private Objective obj;
    private int       rowCount;
    private int       colCount;
    private String    rowName;
    private String    colName;

    private OnLinearProblemUseCaseListener listener;

    private List<List<ICellEntity>> prob;

    private WeakReference<Context> contextRef;

    public LinearProblemUseCase(Context context) {
        this.contextRef = context != null ? new WeakReference<>(context) : null;
    }

    @Override public void setObjective(Objective obj) {
        this.obj = obj;
    }

    @Override public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override public void setColCount(int colCount) {
        this.colCount = colCount;
        INDEX_COLM_DATA = colCount - 2;//critical //todo this means we are at BOUND_ROW
        INDEX_COLM_LAST = colCount - 1;//critical //todo this means we are at RHS_ROW
    }

    @Override public void setRowName(String strRowName) {
        this.rowName = strRowName;
    }

    @Override public void setColName(String strColName) {
        this.colName = strColName;
    }

    @Override public void setListener(OnLinearProblemUseCaseListener listener) {
        this.listener = listener;
    }

    @Override public Context getContext() {
        return contextRef != null ? contextRef.get() : null;
    }

    @Override public void execute() {
        if(Collections.isNullOrEmpty(prob)) {
            prob = new ArrayList<>(rowCount);
            for (int i = 0; i < rowCount; i++) {
                List<ICellEntity> col = new ArrayList<>(colCount);
                for (int j = 0; j < colCount; j++) {
                    createViewLayoutData(i, j, col);
                }
                prob.add(col);
            }
        }
    }

    /**
     * uses observable of rxJava library to work on non-uiThread
     */
    @Override public void executeAsync() {
        Observable.just(prob == null)
                  .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                      @Override public Observable<Boolean> call(Boolean aBoolean) {
                          if(aBoolean) {
                              //we use previous if we do not have any other
                              execute();//just needed to wrap code in non uiThread
                          }
                          return Observable.just(Boolean.TRUE);
                      }
                  })
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Action1<Boolean>() {
                      @Override public void call(Boolean aBoolean) {
                          if (listener != null) {
                              listener.sucess(prob);
                          }
                      }
                  }, new Action1<Throwable>() {
                      @Override public void call(Throwable throwable) {
                          if(listener != null) {
                              listener.error(throwable);
                          }
                      }
                  });
    }

    void createViewLayoutData(int i, int j, List<ICellEntity> dataSet) {
        if (j == INDEX_COLM_START) {
            if(i == INDEX_ROW_LABEL) {
                dataSet.add(EmptyCell.newEmptyCellEntity());
            } else if(i == INDEX_ROW_OBJECTIVE) {
                dataSet.add(LabelCell.newLabelCellEntity(obj.name()));
            } else {
                dataSet.add(LabelCell.newLabelCellEntity(parseRowNameAt(i - 1)));
            }
        } else if(j < INDEX_COLM_DATA) {
            if(i == INDEX_ROW_LABEL) {
                dataSet.add(LabelCell.newLabelCellEntity(parseColNameAt(j)));
            } else {
                dataSet.add(TextCell.newTextCellEntity(0.0d));
            }
        } else if(j == INDEX_COLM_DATA) {
            if(i == INDEX_ROW_LABEL || i == INDEX_ROW_OBJECTIVE) {
                dataSet.add(EmptyCell.newEmptyCellEntity());
            } else {
                dataSet.add(BoundCell.newBoundCellEntity(readBoundList(R.array.rhs_signs)));
            }
        } else if(j == INDEX_COLM_LAST) {
            if(i == INDEX_ROW_LABEL) {
                dataSet.add(LabelCell.newLabelCellEntity(readRHSLabel(R.string.lbl_rhs)));
            } else if(i == INDEX_ROW_OBJECTIVE) {
                dataSet.add(EmptyCell.newEmptyCellEntity());
            } else {
                dataSet.add(TextCell.newTextCellEntity(0.0d));
            }
        }
    }

    String parseRowNameAt(int i) {
        return String.format(Locale.getDefault(),
                             rowName,
                             i);
    }

    String parseColNameAt(int j) {
        return String.format(Locale.getDefault(),
                             colName,
                             j);
    }

    String readRHSLabel(int id) {
        if(getContext() != null) {
            return getContext().getResources().getString(id);
        }
        return null;
    }

    List<String> readBoundList(int id) {
        if (getContext() != null) {
            return Arrays.asList(getContext().getResources().getStringArray(id));
        }
        return null;
    }

    protected void log(String msg) {
        log(Log.DEBUG, msg);
    }

    protected void log(Exception exp) {
        StringWriter strWriter = new StringWriter(128);
        PrintWriter ptrWriter = new PrintWriter(strWriter);
        exp.printStackTrace(ptrWriter);
        log(Log.ERROR, strWriter.toString());
    }

    protected void log(int lv, String msg) {
        if (isLogEnabled()) {
            Log.println(lv, getClassTag(), msg);
        }
    }

    protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    protected String getClassTag() {
        return LinearProblemUseCase.class.getSimpleName();
    }
}