package org.fs.qm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.fs.ndk.ISolver;
import org.fs.qm.entities.Objective;
import org.fs.qm.model.Constraint;
import org.fs.qm.model.FnObjective;
import org.fs.qm.model.FnSubject;
import org.fs.qm.model.IProblem;
import org.fs.qm.model.Simplex;
import org.fs.qm.model.Variable;

import java.util.Arrays;

/**
 * Created by Fatih on 19/06/16.
 * as org.fs.qm.TestActivity
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        //selected RowCount + 2, selected ColCount + 3 should be passed into defineProblemFragment
        //DefineLinearProblemFragmentView frag = DefineLinearProblemFragmentView.newInstance(Objective.MAXIMIZE, 5 + 2, 3 + 3, "Constraint%d", "x%d");
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragDefineLinearProblem, frag).commit();
        Variable var1 = new Variable.Builder().name("x1").index(1).coef(10d).bound(ISolver.Bound.LOWER).lhs(0d).rhs(0d).build();
        Variable var2 = new Variable.Builder().name("x2").index(2).coef(6d).bound(ISolver.Bound.LOWER).lhs(0d).rhs(0d).build();

        //MAX z = 10x1 + 6x2 + 4x3
        FnObjective obj = new FnObjective.Builder().name("simplex").type(Objective.MAXIMIZE).vars(Arrays.asList(var1, var2)).build();



        Variable   con1Var1 = new Variable.Builder().name("x1").index(1).coef(1d).build();
        Variable   con1Var2 = new Variable.Builder().name("x2").index(2).coef(1d).build();
        Constraint con1 = new Constraint.Builder().name("p").index(1).lhs(0d).rhs(100d).bound(ISolver.Bound.UPPER).vars(Arrays.asList(con1Var1, con1Var2)).build();

        Variable   con2Var1 = new Variable.Builder().name("x1").index(1).coef(10d).build();
        Variable   con2Var2 = new Variable.Builder().name("x2").index(2).coef(4d).build();
        Constraint con2 = new Constraint.Builder().name("q").index(2).lhs(0d).rhs(600d).bound(ISolver.Bound.UPPER).vars(Arrays.asList(con2Var1, con2Var2)).build();

        Variable   con3Var1 = new Variable.Builder().name("x1").index(1).coef(2d).build();
        Variable   con3Var2 = new Variable.Builder().name("x2").index(2).coef(2d).build();
        Constraint con3 = new Constraint.Builder().name("r").index(3).lhs(0d).rhs(300d).bound(ISolver.Bound.UPPER).vars(Arrays.asList(con3Var1, con3Var2)).build();

        //Subject to
            //p = x1 + x2 + x3      <= 100
            //q = 10x1 + 4x2 + 5x3  <= 600
            //r = 2x1 + 2x2 + 6x3   <= 300
        FnSubject sbj = new FnSubject.Builder().cons(Arrays.asList(con1, con2, con3)).build();
        IProblem problem = new Simplex(obj, sbj);
        problem.solveAsync();
    }
}
