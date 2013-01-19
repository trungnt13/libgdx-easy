LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
 
LOCAL_MODULE    := enative
LOCAL_C_INCLUDES := 
 
LOCAL_CFLAGS := $(LOCAL_C_INCLUDES:%=-I%) -O2 -Wall -D__ANDROID__
LOCAL_CPPFLAGS := $(LOCAL_C_INCLUDES:%=-I%) -O2 -Wall -D__ANDROID__
LOCAL_LDLIBS := -lm
LOCAL_ARM_MODE  := arm
 
LOCAL_SRC_FILES := Math/eMath.cpp\
			Math/GridSimulationcpp\
			Math/Shape.cpp\
			Math/Vector2.cpp\
			Utils/Array.cpp\
			Utils/List.cpp\
			Utils/Memory.cpp\
			Utils/utils.cpp\
			Sprite/CollisionProcessor.cpp\
			Sprite/Pol2D.cpp\
			Sprite/SpriteBackend.cpp\
			Sprite/SpriteManager.cpp\
			Sprite/SpriteProcessor.cpp
			
include $(BUILD_SHARED_LIBRARY)
