//
// Created by Fatih Şen on 08/12/15.
//

#include <jni.h>
#include <android/log.h>
#include <stdint.h>
#include "glpk/glpk.h"

#define TAG                          "SIMPLEX_JNI"
#define CLASS_NAME                   "org/fs/ndk/SimplexSolver"
#define STORAGE_NAME                 "nativeStorage"

#define N_ELEMENTS(array)            sizeof(array) / sizeof(array[0])
#define L_ERROR(...)                 __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

#define SAVE_SIMPLEX(env, thiz, prob)   (* env)->SetLongField(env, thiz, storage, (jlong) (intptr_t) prob);
#define RESTORE_SIMPLEX(env, thiz)      (simplex_t *) (intptr_t) (* env)->GetLongField(env, thiz, storage);

typedef glp_prob simplex_t;

/***
 * methods
 */
static void     create_simplex       (JNIEnv * env, jobject thiz);
static void     set_simplex_name     (JNIEnv * env, jobject thiz, jstring name);
static void     set_obj_type         (JNIEnv * env, jobject thiz, jint type);
static void     add_rows             (JNIEnv * env, jobject thiz, jint rows);
static void     set_row_name         (JNIEnv * env, jobject thiz, jint index, jstring name);
static void     set_row_bounds       (JNIEnv * env, jobject thiz, jint index, jint type, jdouble lhs, jdouble rhs);
static void     add_cols             (JNIEnv * env, jobject thiz, jint cols);
static void     set_col_name         (JNIEnv * env, jobject thiz, jint index, jstring name);
static void     set_col_bounds       (JNIEnv * env, jobject thiz, jint index, jint type, jdouble lhs, jdouble rhs);
static void     set_col_coef         (JNIEnv * env, jobject thiz, jint index, jdouble coef);
static void     load_matrix          (JNIEnv * env, jobject thiz, jint size,  jintArray ia, jintArray ja, jdoubleArray ar);
static void     solve_simplex        (JNIEnv * env, jobject thiz);
static jint     simplex_status       (JNIEnv * env, jobject thiz);
static jint     simplex_prim_status  (JNIEnv * env, jobject thiz);
static jint     simplex_dual_status  (JNIEnv * env, jobject thiz);
static jdouble  simplex_z            (JNIEnv * env, jobject thiz);
static jdouble  simplex_v            (JNIEnv * env, jobject thiz, jint index);
static jint     simplex_row_status   (JNIEnv * env, jobject thiz, jint index);
static jdouble  simplex_row_prim     (JNIEnv * env, jobject thiz, jint index);
static jdouble  simplex_row_dual     (JNIEnv * env, jobject thiz, jint index);
static jint     simplex_col_status   (JNIEnv * env, jobject thiz, jint index);
static jdouble  simplex_col_prim     (JNIEnv * env, jobject thiz, jint index);
static jdouble  simplex_col_dual     (JNIEnv * env, jobject thiz, jint index);
static void     delete_simplex       (JNIEnv * env, jobject thiz);

static jfieldID         storage;
static JNINativeMethod  array[] = {
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
};

jint
JNI_OnLoad(JavaVM * vm, void * reserved)
{
    JNIEnv * env = NULL;
    if((* vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        L_ERROR("jni v1.4 is not supported!");
        return JNI_EVERSION; //special error that is jni version error
    }

    jclass clazz  = (* env)->FindClass(env, CLASS_NAME);
    if(!clazz) {
        L_ERROR("%s does not exist!", CLASS_NAME);
        return JNI_ERR;
    }

    (* env)->RegisterNatives(env, clazz, array, N_ELEMENTS(array));
    storage = (* env)->GetFieldID(env, clazz, STORAGE_NAME, "J");

    if(!storage) {
        L_ERROR("%s does not exist!", STORAGE_NAME);
        return JNI_ERR;
    }
    return JNI_VERSION_1_4;
}

static void
create_simplex(JNIEnv * env, jobject thiz)
{
    simplex_t * prob = glp_create_prob();
    SAVE_SIMPLEX(env, thiz, prob);
}

static void
set_simplex_name(JNIEnv * env, jobject thiz, jstring name)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    const char * text = (* env)->GetStringUTFChars(env, name, 0);
    glp_set_prob_name(prob, text);
    (* env)->ReleaseStringUTFChars(env, name, text);
}

static void
set_obj_type(JNIEnv * env, jobject thiz, jint type)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    glp_set_obj_dir(prob, (int) type);
}

static void
add_rows(JNIEnv * env, jobject thiz, jint rows)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    glp_add_rows(prob, (int) rows);
}

static void
set_row_name(JNIEnv * env, jobject thiz, jint index, jstring name)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    const char * text = (* env)->GetStringUTFChars(env, name, 0);
    glp_set_row_name(prob, (int) index, text);
    (* env)->ReleaseStringUTFChars(env, name, text);
}

static void
set_row_bounds(JNIEnv * env, jobject thiz, jint index, jint type, jdouble lhs, jdouble rhs)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    glp_set_row_bnds(prob, (int) index, (int) type, (double) lhs, (double) rhs);
}

static void
add_cols(JNIEnv * env, jobject thiz, jint index)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    glp_add_cols(prob, (int) index);
}

static void
set_col_name(JNIEnv * env, jobject thiz, jint index, jstring name)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    const char * text = (* env)->GetStringUTFChars(env, name, 0);
    glp_set_col_name(prob, (int) index, text);
    (* env)->ReleaseStringUTFChars(env, name, text);
}

static void
set_col_bounds(JNIEnv * env, jobject thiz, jint index, jint type, jdouble lhs, jdouble rhs)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    glp_set_col_bnds(prob, (int) index, (int) type, (double) lhs, (double) rhs);
}

static void
set_col_coef(JNIEnv * env, jobject thiz, jint index, jdouble coef)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    glp_set_obj_coef(prob, (int) index, (double) coef);
}

static void
load_matrix(JNIEnv * env, jobject thiz, jint size, jintArray ai, jintArray aj, jdoubleArray ra)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    jint * iarray;
    jsize len = (* env)->GetArrayLength(env, ai);
    int ia[len], ja[len], i;
    double ar[len];
    iarray = (* env)->GetIntArrayElements(env, ai, 0);
    for(i = 0; i < len; i++) {
        ia[i] = (int) iarray[i];
    }
    (* env)->ReleaseIntArrayElements(env, ai, iarray, 0);
    //catch - release
    jint * jarray;
    len = (* env)->GetArrayLength(env, aj);
    jarray = (* env)->GetIntArrayElements(env, aj, 0);
    for(i = 0; i < len; i++) {
        ja[i] = (int) jarray[i];
    }
    (* env)->ReleaseIntArrayElements(env, aj, jarray, 0);
    //catch - release
    jdouble * rarray;
    rarray = (* env)->GetDoubleArrayElements(env, ra, 0);
    for(i = 0; i < (int) size; i++) {
        ar[i] = (double) rarray[i];
    }
    (* env)->ReleaseDoubleArrayElements(env, ra, rarray, 0);
    //load those
    glp_load_matrix(prob, (int) size - 1, ia, ja, ar);
}

static void
solve_simplex(JNIEnv * env, jobject thiz)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    glp_simplex(prob, NULL);
}

static jint
simplex_status(JNIEnv * env, jobject thiz)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jint) glp_get_status(prob);
}

static jint
simplex_prim_status(JNIEnv * env, jobject thiz)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jint) glp_get_prim_stat(prob);
}

static jint
simplex_dual_status(JNIEnv * env, jobject thiz)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jint) glp_get_dual_stat(prob);
}

static jdouble
simplex_z(JNIEnv * env, jobject thiz)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jdouble) glp_get_obj_val(prob);
}

static jdouble
simplex_v(JNIEnv * env, jobject thiz, jint index)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jdouble) glp_get_col_prim(prob, (int) index);
}

static jint
simplex_row_status(JNIEnv * env, jobject thiz, jint index)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jint) glp_get_row_stat(prob, (int) index);
}

static jdouble
simplex_row_prim(JNIEnv * env, jobject thiz, jint index)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jdouble) glp_get_row_prim(prob, (int) index);
}

static jdouble
simplex_row_dual(JNIEnv * env, jobject thiz, jint index)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jdouble) glp_get_row_dual(prob, (int) index);
}

static jint
simplex_col_status(JNIEnv * env, jobject thiz, jint index)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jint) glp_get_col_stat(prob, (int) index);
}

static jdouble
simplex_col_prim(JNIEnv * env, jobject thiz, jint index)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jdouble) glp_get_col_prim(prob, (int) index);
}

static jdouble
simplex_col_dual(JNIEnv * env, jobject thiz, jint index)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    return (jdouble) glp_get_col_dual(prob, (int) index);
}

static void
delete_simplex(JNIEnv * env, jobject thiz)
{
    simplex_t * prob = RESTORE_SIMPLEX(env, thiz);
    glp_delete_prob(prob);
    SAVE_SIMPLEX(env, thiz, NULL);//clear java part too
}