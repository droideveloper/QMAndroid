package org.fs.qm.presenters;

import android.os.Bundle;

import org.fs.common.IPresenter;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.presenters.IDefineLinearProblemFragmentPresenter
 */
public interface IDefineLinearProblemFragmentPresenter extends IPresenter {

    void restoreState(Bundle restore);
    void storeState(Bundle store);
    void onBackPressed();
}