LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_PACKAGE_NAME := CustomDream
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_MODULES_TAGS := optional
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)