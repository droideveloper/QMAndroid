LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    	:= ndkSimplex
LOCAL_SRC_FILES 	:= glpk_simplex_jni.c
LOCAL_C_INCLUDES 	:= $(LOCAL_PATH)

LOCAL_SRC_FILES		+= $(wildcard $(LOCAL_PATH)/glpk/*.c)
LOCAL_C_INCLUDES	+= $(LOCAL_PATH)/glpk

LOCAL_LDLIBS		:= -llog

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)

LOCAL_MODULE    	:= ndkMips
LOCAL_SRC_FILES 	:= glpk_mips_jni.c
LOCAL_C_INCLUDES 	:= $(LOCAL_PATH)

LOCAL_SRC_FILES		+= $(wildcard $(LOCAL_PATH)/glpk/*.c)
LOCAL_C_INCLUDES	+= $(LOCAL_PATH)/glpk

LOCAL_LDLIBS		:= -llog

include $(BUILD_SHARED_LIBRARY)