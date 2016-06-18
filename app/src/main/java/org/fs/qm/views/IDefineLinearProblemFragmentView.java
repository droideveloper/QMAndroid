package org.fs.qm.views;

import android.content.Context;
import android.view.View;

import org.fs.common.IView;
import org.fs.qm.adapters.GridRecyclerAdapter;
import org.fs.qm.components.ActivityComponent;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.views.IDefineLinearProblemFragmentView
 */
public interface IDefineLinearProblemFragmentView extends IView {

    View onBindViews(View view);
    void bindAdapter(GridRecyclerAdapter adapter);

    ActivityComponent   getActivityComponent();
    Context             getContext();
}