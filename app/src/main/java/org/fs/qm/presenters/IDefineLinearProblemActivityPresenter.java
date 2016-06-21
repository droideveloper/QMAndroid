package org.fs.qm.presenters;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.fs.common.IPresenter;

/**
 * Created by Fatih on 22/06/16.
 * as org.fs.qm.presenters.IDefineLinearProblemActivityPresenter
 */
public interface IDefineLinearProblemActivityPresenter extends IPresenter {

    void restoreState(Bundle input);
    void storeState(Bundle output);
    void onBackPressed();

    Toolbar.OnClickListener navigationListener();
    View.OnClickListener    clickListener();
}