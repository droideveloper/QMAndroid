package org.fs.qm.usecases;

import android.content.Context;

import org.fs.common.IUseCase;
import org.fs.qm.entities.ICellEntity;
import org.fs.qm.entities.Objective;

import java.util.List;

/**
 * Created by Fatih on 19/06/16.
 * as org.fs.qm.usecases.ILinearProblemUseCase
 */
public interface ILinearProblemUseCase extends IUseCase {

    void setObjective(Objective obj);
    void setRowCount(int rowCount);
    void setColCount(int colCount);
    void setRowName(String strRowName);
    void setColName(String strColName);
    void setListener(OnLinearProblemUseCaseListener listener);

    void executeAsync();

    Context getContext();

    interface OnLinearProblemUseCaseListener {
        void sucess(List<List<ICellEntity>> dataSet);
        void error(Throwable exp);
    }
}