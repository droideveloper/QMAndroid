package org.fs.ndk;

import android.support.annotation.IntRange;

/**
 * Created by Fatih on 09/12/15.
 * as org.fs.ndk.SimplexSolver
 */
final class SimplexSolver implements ISolver {

    static {
        System.loadLibrary("ndkSimplex");
    }

  /*
     { "nativeCreate",       "()V",                      (void *) create_simplex },
     { "nativeSetName",      "(Ljava/lang/String;)V",    (void *) set_simplex_name },
     { "nativeSetType",      "(I)V",                     (void *) set_obj_type },
     { "nativeAddRows",      "(I)V",                     (void *) add_rows },
     { "nativeRowName",      "(ILjava/lang/String;)V",   (void *) set_row_name },
     { "nativeRowBound",     "(IIDD)V",                  (void *) set_row_bounds },
     { "nativeAddCols",      "(I)V",                     (void *) add_cols },
     { "nativeColName",      "(ILjava/lang/String;)V",   (void *) set_col_name },
     { "nativeColBound",     "(IIDD)V",                  (void *) set_col_bounds },
     { "nativeColCoef",      "(ID)V",                    (void *) set_col_coef },
     { "nativeLoadMatrix",   "(I[I[I[D)V",               (void *) load_matrix },
     { "nativeSolve",        "()V",                      (void *) solve_simplex },
     { "nativeStatus",       "()I",                      (void *) simplex_status },
     { "nativePrimStatus",   "()I",                      (void *) simplex_prim_status },
     { "nativeDualStatus",   "()I",                      (void *) simplex_dual_status },
     { "nativeZ",            "()D",                      (void *) simplex_z },
     { "nativeV",            "(I)D",                     (void *) simplex_v },
     { "nativeRowStatus",    "(I)I",                     (void *) simplex_row_status },
     { "nativeRowPrim",      "(I)D",                     (void *) simplex_row_prim },
     { "nativeRowDual",      "(I)D",                     (void *) simplex_row_dual },
     { "nativeColStatus",    "(I)I",                     (void *) simplex_col_status },
     { "nativeColPrim",      "(I)D",                     (void *) simplex_col_prim },
     { "nativeColDual",      "(I)D",                     (void *) simplex_col_dual },
     { "nativeDelete",       "()V",                      (void *) delete_simplex }
  */
    /*
        nativeStorage field is used by native code to store relative problem data as pointer
        kinda hot stuff
     */
    private long nativeStorage; /* long used as memory spot for simplex_t */

    /*
        fields will be stored that might be used for database segment
     */


    @Override
    public void create() {
        nativeCreate();
    }

    @Override
    public void setProblemName(String name) {
        nativeSetName(name);
    }

    @Override
    public void setProblemType(@IntRange(from = Constants.MIN, to = Constants.MAX) int type) {
        nativeSetType(type);
    }

    @Override
    public void addNumberOfRows(int rows) {
        nativeAddRows(rows);
    }

    @Override
    public void addRowName(int index, String name) {
        nativeRowName(index, name);
    }

    @Override
    public void addRowBound(int index, @IntRange(from = Constants.FREE, to = Constants.FIXED) int bound, double lhs, double rhs) {
        nativeRowBound(index, bound, lhs, rhs);
    }

    @Override
    public void addNumberOfCols(int cols) {
        nativeAddCols(cols);
    }

    @Override
    public void addColName(int index, String name) {
        nativeColName(index, name);
    }

    @Override
    public void addColBound(int index, @IntRange(from = Constants.FREE, to = Constants.FIXED) int bound, double lhs, double rhs) {
        nativeColBound(index, bound, lhs, rhs);
    }

    @Override
    public void addColCoef(int index, double coef) {
        nativeColCoef(index, coef);
    }

    @Override
    public void addColType(int index, @IntRange(from = Constants.CONTINUOUS, to = Constants.BINARY) int type) {
        throw new IllegalArgumentException("Simplex does not support this method!");
    }

    @Override
    public void loadMatrix(int size, int[] ai, int[] aj, double[] ar) {
        nativeLoadMatrix(size, ai, aj, ar);
    }

    @Override
    public void solve() {
        nativeSolve();
    }

    @Override
    public int status() {
        return nativeStatus();
    }

    @Override
    public int primStatus() {
        return nativePrimStatus();
    }

    @Override
    public int dualStatus() {
        return nativeDualStatus();
    }

    @Override
    public double z() {
        return nativeZ();
    }

    @Override
    public double v(int index) {
        return nativeV(index);
    }

    @Override
    public int rowStatus(int index) {
        return nativeRowStatus(index);
    }

    @Override
    public double rowPrim(int index) {
        return nativeRowPrim(index);
    }

    @Override
    public double rowDual(int index) {
        return nativeRowDual(index);
    }

    @Override
    public int colStatus(int index) {
        return nativeColStatus(index);
    }

    @Override
    public double colPrim(int index) {
        return nativeColPrim(index);
    }

    @Override
    public double colDual(int index) {
        return nativeColDual(index);
    }

    @Override
    public void delete() {
        nativeDelete();
    }

    private native void     nativeCreate();
    private native void     nativeSetName(final String name);
    private native void     nativeSetType(final int type);
    private native void     nativeAddRows(final int rows);
    private native void     nativeRowName(final int index, final String name);
    private native void     nativeRowBound(final int index, final int bound, final double lhs, final double rhs);
    private native void     nativeAddCols(final int cols);
    private native void     nativeColName(final int index, final String name);
    private native void     nativeColBound(final int index, final int bound, final double lhs, final double rhs);
    private native void     nativeColCoef(final int index, final double coef);
    private native void     nativeLoadMatrix(final int size, final int[] ai, final int[] aj, final double[] ar);
    private native void     nativeSolve();
    private native int      nativeStatus();
    private native int      nativePrimStatus();
    private native int      nativeDualStatus();
    private native double   nativeZ();
    private native double   nativeV(final int index);
    private native int      nativeRowStatus(final int index);
    private native double   nativeRowPrim(final int index);
    private native double   nativeRowDual(final int index);
    private native int      nativeColStatus(final int index);
    private native double   nativeColPrim(final int index);
    private native double   nativeColDual(final int index);
    private native void     nativeDelete();
}
