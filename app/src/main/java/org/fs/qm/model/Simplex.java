package org.fs.qm.model;

import android.util.Log;

import org.fs.ndk.ISolver;
import org.fs.ndk.SolverFactory;
import org.fs.qm.entities.Objective;
import org.fs.util.PreconditionUtility;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.Simplex
 */
public class Simplex implements IProblem {

    //this will solve the problem
    private ISolver simplexProxy;

    private FnObjective obj;
    private FnSubject   sbj;

    public Simplex() {
        //create solver
        simplexProxy = SolverFactory.create(SolverFactory.SIMPLEX);
    }

    public Simplex(FnObjective obj, FnSubject sbj) {
        this();
        this.obj = obj;
        this.sbj = sbj;
    }

    @Override public void solve() {
        loadProblem();
    }

    @Override public void solveAsync() {
        //todo implement
    }


    void loadProblem() {
        PreconditionUtility.checkNotNull(obj, "objective function is null");
        PreconditionUtility.checkNotNull(sbj, "subject to function is null");
        //create problem
        simplexProxy.create();
        //set problem name
        simplexProxy.setProblemName(obj.getName());
        //set min or max
        simplexProxy.setProblemType(obj.getType().equals(Objective.MAXIMIZE) ? ISolver.Constants.MAX : ISolver.Constants.MIN);
        //add row size, which is constraints count
        simplexProxy.addNumberOfRows(sbj.constraintsSize());
        //add each constraint into problem
        for (int i = 0; i < sbj.constraintsSize(); i++) {
            Constraint con = sbj.constraintAt(i);
            if(con != null) {
                //add constraint name
                simplexProxy.addRowName(con.getIndex(), con.getName());
                //add value of constraint
                simplexProxy.addRowBound(con.getIndex(), con.getBound(), con.getLhs(), con.getRhs());
            }
        }
        //add colm number
        simplexProxy.addNumberOfCols(obj.variablesSize());
        //add each variable in objective function
        for (int i = 0; i < obj.variablesSize(); i++) {
            Variable var = obj.variableAt(i);
            if (var != null) {
                //add var name
                simplexProxy.addColName(var.getIndex(), var.getName());
                //add value of var
                simplexProxy.addColBound(var.getIndex(), var.getBound(), var.getLhs(), var.getRhs());
                //add coef of var
                simplexProxy.addColCoef(var.getIndex(), var.getCoef());
            }
        }
        //load constraints into matrix
        int n = obj.variablesSize() * sbj.constraintsSize();
        int[]       ia = new int[n + 1];//row index
        int[]       ja = new int[n + 1];//col index
        double[]    ar = new double[n + 1];//coef at (x, y)

        int i = 1;
        for (Constraint c : sbj.getCons()) {
            for (Variable var : c.getVars()) {
                ia[i] = c.getIndex();
                ja[i] = var.getIndex();
                ar[i] = var.getCoef();
                i++;
            }
        }
        simplexProxy.loadMatrix(n, ia, ja, ar);
        simplexProxy.solve();
        log(Log.ERROR, "solution is : " + simplexProxy.z());
        simplexProxy.delete();
    }

    @Override public ISolution getSolution() {
        //todo implement
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
        return Boolean.TRUE;
    }

    protected String getClassTag() {
        return Simplex.class.getSimpleName();
    }

    //todo implement private class as SimplexSolution implements ISolution
}
