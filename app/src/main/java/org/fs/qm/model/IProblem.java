package org.fs.qm.model;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.IProblem
 */
public interface IProblem {

    void solve();
    void solveAsync();

    ISolution getSolution();

}
