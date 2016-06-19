package org.fs.qm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.fs.qm.entities.Objective;
import org.fs.qm.views.DefineLinearProblemFragmentView;

/**
 * Created by Fatih on 19/06/16.
 * as org.fs.qm.TestActivity
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        DefineLinearProblemFragmentView frag = DefineLinearProblemFragmentView.newInstance(Objective.MAXIMIZE, 5 + 2, 3 + 3, "Constraint%d", "x%d");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragDefineLinearProblem, frag).commit();
    }
}
