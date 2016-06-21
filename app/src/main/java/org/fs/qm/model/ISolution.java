package org.fs.qm.model;

import org.fs.ndk.ISolver;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.ISolution
 */
public interface ISolution {

    void    loadSolution(ISolver solver);

    boolean isGraphAvailable();
    int     solutionStatus();
    double  solutionZ();
    double  solutionZforVarAt(int index);

}
