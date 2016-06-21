package org.fs.ndk;

import android.support.annotation.IntRange;

/**
 * Created by Fatih on 09/12/15.
 * as org.fs.ndk.ISolver
 */
public interface ISolver {

    class Objective {
        public final static int MIN = 1;
        public final static int MAX = 2;
    }

    class Bound {
        public final static int FREE   = 1;
        public final static int LOWER  = 2;
        public final static int UPPER  = 3;
        public final static int DOUBLE = 4;
        public final static int FIXED  = 5;
    }

    class Variable {
        public final static int CONTINUOUS   = 1;
        public final static int INTEGER      = 2;
        public final static int BINARY       = 3;
    }

    class Solution {
        public final static int UNDEFINED       = 1;
        public final static int FEASIBLE        = 2;
        public final static int INFEASIBLE      = 3;
        public final static int NOFEASIBLE      = 4;
        public final static int OPTIMAL         = 5;
        public final static int UNBOUNDED       = 6;
    }

    class Stats {
        public final static int BASIC           = 1;
        public final static int NON_BASIC_LOWER = 2;
        public final static int NON_BASIC_UPPER = 3;
        public final static int NON_BASIC_FREE  = 4;
        public final static int NON_BASIC_FIXED = 5;
    }

    @Deprecated
    class Constants {
        /**
         * Problem Type
         */
        public final static int MIN = 1;
        public final static int MAX = 2;

        /**
         * Bounds
         */
        public final static int FREE   = 1;
        public final static int LOWER  = 2;
        public final static int UPPER  = 3;
        public final static int DOUBLE = 4;
        public final static int FIXED  = 5;

        /**
         * Variable
         */
        public final static int CONTINUOUS   = 1;
        public final static int INTEGER      = 2;
        public final static int BINARY       = 3;

        /**
         * Solution
         */
        public final static int UNDEFINED       = 1;
        public final static int FEASIBLE        = 2;
        public final static int INFEASIBLE      = 3;
        public final static int NOFEASIBLE      = 4;
        public final static int OPTIMAL         = 5;
        public final static int UNBOUNDED       = 6;

        /**
         * Row and Col Status
         */
        public final static int BASIC           = 1;
        public final static int NON_BASIC_LOWER = 2;
        public final static int NON_BASIC_UPPER = 3;
        public final static int NON_BASIC_FREE  = 4;
        public final static int NON_BASIC_FIXED = 5;
    }

    void create();

    void setProblemName(final String name);

    void setProblemType(@IntRange(from = Objective.MIN, to = Objective.MAX) final int type);

    void addNumberOfRows(final int rows);

    void addRowName(final int index, final String name);

    void addRowBound(final int index, @IntRange(from = Bound.FREE, to = Bound.FIXED) final int bound, final double lhs, final double rhs);

    void addNumberOfCols(final int cols);

    void addColName(final int index, final String name);

    void addColBound(final int index, @IntRange(from = Bound.FREE, to = Bound.FIXED) final int bound, final double lhs, final double rhs);

    void addColCoef(final int index, final double coef);

    void addColType(final int index, @IntRange(from = Variable.CONTINUOUS, to = Variable.BINARY) final int type);

    void loadMatrix(final int size, int[] ai, int[] aj, double[] ar);

    void solve();

    @IntRange(from = Solution.UNDEFINED, to = Solution.UNBOUNDED)
    int status();

    @IntRange(from = Solution.UNDEFINED, to = Solution.NOFEASIBLE)
    int primStatus();

    @IntRange(from = Solution.UNDEFINED, to = Solution.NOFEASIBLE)
    int dualStatus();

    double z();

    double v(final int index);

    @IntRange(from = Stats.BASIC, to = Stats.NON_BASIC_FIXED)
    int rowStatus(final int index);

    double rowPrim(final int index);

    double rowDual(final int index);

    @IntRange(from = Stats.BASIC, to = Stats.NON_BASIC_FIXED)
    int colStatus(int index);

    double colPrim(final int index);

    double colDual(final int index);

    void delete();
}
