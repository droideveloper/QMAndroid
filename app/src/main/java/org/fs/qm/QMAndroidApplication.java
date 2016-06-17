package org.fs.qm;

import org.fs.core.AbstractApplication;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.QMAndroidApplication
 */
public class QMAndroidApplication extends AbstractApplication {

    @Override public void onCreate() {
        super.onCreate();
    }

    @Override protected String getClassTag() {
        return QMAndroidApplication.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return isDebug();
    }
}