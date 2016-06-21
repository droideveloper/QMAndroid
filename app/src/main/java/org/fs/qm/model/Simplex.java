package org.fs.qm.model;

import android.support.annotation.IntRange;
import android.util.Log;

import org.fs.ndk.ISolver;
import org.fs.ndk.SolverFactory;
import org.fs.qm.entities.Objective;
import org.fs.util.PreconditionUtility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.Simplex
 */
public class Simplex implements IProblem {

    //this will solve the problem
    private ISolver simplexProxy;

    private ISolution solutionProxy;

    private Callback  callback;

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

    @Override public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override public void solve() {
        loadProblem();
    }

    @Override public void solveAsync() {
        PreconditionUtility.checkNotNull(callback, "you should set callback for async computation");
        Observable.just(Boolean.TRUE)
                  .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                      @Override public Observable<Boolean> call(Boolean aBoolean) {
                          solve();
                          return Observable.just(Boolean.TRUE);
                      }
                  })
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Action1<Boolean>() {
                      @Override
                      public void call(Boolean aBoolean) {
                          if (callback != null) {
                              callback.sucess(solutionProxy);
                          }
                      }
                  }, new Action1<Throwable>() {
                      @Override public void call(Throwable throwable) {
                          if (callback != null) {
                              callback.error(throwable);
                          }
                      }
                  });
    }


    void loadProblem() {
        PreconditionUtility.checkNotNull(obj, "objective function is null");
        log(Log.ERROR, obj.toString());
        PreconditionUtility.checkNotNull(sbj, "subject to function is null");
        log(Log.ERROR, sbj.toString());
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
        //calc constraints value
        solutionProxy.loadConstraints(sbj.getCons());
        //if suitable for graph, make calc
        if(solutionProxy.isGraphAvailable()) {
            solutionProxy.loadGraph(sbj.getCons());
        }

//        for (Constraint c: sbj.getCons()) {
//            double[] coefs = c.variablesCoef();
//
//            log(Log.ERROR, c.getName() + "*****starts*****");
//            double total = 0.0d;
//            for(i = 0; i < coefs.length; i++) {
//                log(Log.ERROR, String.format(Locale.US,
//                                             "%f x %f", coefs[i], solutionProxy.solutionZforVarAt(i)));
//                total+= coefs[i] * solutionProxy.solutionZforVarAt(i);
//            }
//            log(Log.ERROR, c.getName() + String.format(Locale.US, " total: %f", total - c.getRhs()));
//            log(Log.ERROR, c.getName() + "*****ends*****");
//        }
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

    /**
     * Simplex inner solution implementation so far we added
     *  -> Status
     *  -> Variable and Objective results
     *  -> if possible graph coordinates
     *  -> Constraint sums
     */
    private class SimplexSolution implements ISolution {

        private static final int GRAPH_CONSTANT = 0x02;
        private static final int INDEX_X        = 0x00;
        private static final int INDEX_Y        = 0x01;

        //gone hold only these values
        @IntRange(from = ISolver.Solution.UNDEFINED, to = ISolver.Solution.UNBOUNDED)
        private int status;
        private int size;

        private double      z;
        private double[]    zVars;
        private double[]    cSum;//constraint sums
        private Line[]      zGraph;

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

        //do not call before load solution
        //loads each constraint value calculation depending on solution we obtained
        @Override public void loadConstraints(List<Constraint> cons) {
            cSum = new double[cons.size()];
            for (int i = 0; i < cons.size(); i++) {
                Constraint c = cons.get(i);
                if(c != null) {
                    double[] coefs = c.variablesCoef();
                    double sum = 0.0;
                    for (int j = 0; j < coefs.length; j++) {
                        sum+= (coefs[j] * zVars[j]);
                    }
                    cSum[i] = sum;
                }
            }
        }

        @Override public void loadGraph(List<Constraint> cons) {
            zGraph = new Line[cons.size()];
            for (int i = 0; i < cons.size();i++) {
                Constraint c = cons.get(i);
                if(c != null) {
                    //since we only have 2 variable defined in objective function we use macro
                    double[] coefs = c.variablesCoef();//0, 1
                    //calculate y
                    Position yPos = calcPosition(c.getBound() == ISolver.Bound.UPPER, coefs[INDEX_Y], c, INDEX_Y);
                    Position xPos = calcPosition(c.getBound() == ISolver.Bound.UPPER, coefs[INDEX_X], c, INDEX_X);
                    //direction -1 or 1 if -1 is below side, 1 is upper side will be considered
                    zGraph[i] = Line.create(xPos, yPos, c.getBound() == ISolver.Bound.UPPER ? 1 : -1);
                }
            }
        }

        Position calcPosition(boolean upper, double coef, Constraint target, int index) {
            return upper ?
                            (index == INDEX_Y ? Position.create(0d, target.getRhs() / coef)
                                              : Position.create(target.getRhs() / coef, 0d))
                         :  (index == INDEX_Y ? Position.create(0d, target.getLhs() / coef)
                                              : Position.create(target.getLhs() / coef, 0d));
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
            return (zVars != null && index >= 0 && index < size) ? zVars[index] : -1d;
        }

        @Override public double solutionZforConAt(int index) {
            return (cSum != null && index >= 0 && index < cSum.length) ? cSum[index] : -1d;
        }

        @Override public Line solutionLineAt(int index) {
            return (zGraph != null && index >= 0 && index < zGraph.length) ? zGraph[index] : null;
        }
    }

}
