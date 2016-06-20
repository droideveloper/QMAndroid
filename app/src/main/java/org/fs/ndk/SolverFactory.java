package org.fs.ndk;

import android.support.annotation.IntRange;

/**
 * Created by Fatih on 09/12/15.
 * as org.fs.ndk.SolverFactory
 */
public final class SolverFactory {

    public final static int SIMPLEX     = 1;
    public final static int MIPS        = 2;

    public static ISolver create(@IntRange(from = SIMPLEX, to = MIPS) int type) {
        if(type == SIMPLEX) {
            return new SimplexSolver();
        } else {
            return new MipsSolver();
        }
    }

    /**
     * to honor Reflection users, they are funny bunch.
     */
    private SolverFactory() {
        throw new RuntimeException("no instance for ya");
    }
}
