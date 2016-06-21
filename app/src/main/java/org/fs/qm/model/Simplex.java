package org.fs.qm.model;

import android.support.annotation.IntRange;
import android.util.Log;

import org.fs.ndk.ISolver;
import org.fs.ndk.SolverFactory;
import org.fs.qm.entities.Objective;
import org.fs.util.PreconditionUtility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.Simplex
 */
public class Simplex implements IProblem {

    //this will solve the problem
    private ISolver simplexProxy;

    private ISolution solutionProxy;

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
        simplexProxy.setProblemType(obj.getType().equals(Objective.MAXIMIZE) ? ISolver.Objective.MAX : ISolver.Objective.MIN);
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
        //create matrix
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
        solutionProxy = new SimplexSolution(obj.variablesSize());
        solutionProxy.loadSolution(simplexProxy);
        log(Log.ERROR, String.format(Locale.getDefault(), "Z: %f, x1: %f, x2: %f, x3: %f",
                                     solutionProxy.solutionZ(),
                                     solutionProxy.solutionZforVarAt(0),
                                     solutionProxy.solutionZforVarAt(1),
                                     solutionProxy.solutionZforVarAt(2)));

        for (Constraint c: sbj.getCons()) {
            double[] coefs = c.variablesCoef();
//            double sum = coefs[0] * solutionProxy.solutionZforVarAt(0)
//                    + coefs[1] * solutionProxy.solutionZforVarAt(1)
//                    + coefs[2] + solutionProxy.solutionZforVarAt(2);
            log(Log.ERROR, c.getName() + "*****starts*****");
            double total = 0.0d;
            for(i = 0; i < coefs.length; i++) {
                log(Log.ERROR, String.format(Locale.US,
                                             "%f x %f", coefs[i], solutionProxy.solutionZforVarAt(i)));
                total+= coefs[i] * solutionProxy.solutionZforVarAt(i);
            }
            log(Log.ERROR, c.getName() + String.format(Locale.US, " total: %f", total - c.getRhs()));
            log(Log.ERROR, c.getName() + "*****ends*****");
        }
    }

    @Override public ISolution getSolution() {
        return solutionProxy;
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
        return true;
    }

    protected String getClassTag() {
        return Simplex.class.getSimpleName();
    }

    private class SimplexSolution implements ISolution {

        private static final int GRAPH_CONSTANT = 0x02;

        //gone hold only these values
        @IntRange(from = ISolver.Solution.UNDEFINED, to = ISolver.Solution.UNBOUNDED)
        private int status;
        private int size;

        private double      z;
        private double[]    zVars;

        public SimplexSolution(int size) {
            this.size = size;
            this.zVars = new double[size];
        }

        @Override public void loadSolution(ISolver solver) {
            solver.solve();
            status = solver.status();
            z = solver.z();
            for (int i = 1; i <= size; i++) {
                zVars[i - 1] = solver.colPrim(i);
            }
            solver.delete();
        }

        @Override public boolean isGraphAvailable() {
            return size == GRAPH_CONSTANT;
        }

        @Override public int solutionStatus() {
            return status;
        }

        @Override public double solutionZ() {
            return z;
        }

        @Override public double solutionZforVarAt(int index) {
            if(zVars != null && index >= 0 && index < size) {
                return zVars[index];
            }
            return -1d;
        }
    }
}
