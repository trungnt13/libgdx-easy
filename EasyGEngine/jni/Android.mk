LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
 
LOCAL_MODULE    := enative
LOCAL_SRC_FILES := CollisionProcessor.cpp\
	org_ege_utils_CollisionChecker.cpp\
	utils.cpp\
	utils/Array.cpp\
	utils/GridSimulation.cpp\
	utils/math/eMath.cpp\
	utils/math/Vector2.cpp

include $(BUILD_SHARED_LIBRARY)
