package org.fs.qm.model;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.IProblem
 */
public interface IProblem {

    void solve();
    void solveAsync();
    void setCallback(Callback callback);

    ISolution getSolution();

    //async needs this object
    interface Callback {
        void sucess(ISolution solution);
        void error(Throwable exp);
    }
}
