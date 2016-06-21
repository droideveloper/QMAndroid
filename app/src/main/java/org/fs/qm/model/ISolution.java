package org.fs.qm.model;

import org.fs.ndk.ISolver;

import java.util.List;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.ISolution
 */
public interface ISolution {

    void    loadSolution(ISolver solver);
    void    loadGraph(List<Constraint> cons);
    void    loadConstraints(List<Constraint> cons);

    boolean isGraphAvailable();
    int     solutionStatus();
    double  solutionZ();

    //might return -1 if index out of bound
    double      solutionZforVarAt(int index);
    //might return -1 if index out of bound
    double      solutionZforConAt(int index);
    //might return null if index out of bound
    Line        solutionLineAt(int index);
    List<Line>  solutionLines();

}
