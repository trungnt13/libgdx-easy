CC = x86_64-w64-mingw32-g++
CFLAGS = -c -Wall -O2 -mfpmath=sse -msse2 -fmessage-length=0 -m64
JNIFLAGS = -I"C:/Program Files/Java/jdk1.7.0_07/include" -I"C:/Program Files/Java/jdk1.7.0_07/include/win32"
DLLFLAGS = -Wl,--kill-at -shared -static-libgcc -static-libstdc++ -m64 -o
SOLUTIONDIR =  C:/1_VisualStudio2012/Pol2DEngine
SOLUTIONFLAG = -I"${SOLUTIONDIR}"

all: pol2D rm copy

pol2D: okj_easy_graphics_graphics2d_NSpriteDef.o org_ege_utils_SpriteUtils.o org_ege_utils_CollisionChecker.o okj_easy_graphics_graphics2d_NManager.o  okj_easy_graphics_graphics2d_NativeSpriteBackend.o okj_easy_graphics_graphics2d_NWorld.o
	${CC} ${DLLFLAGS} libenative.dll *.o

okj_easy_graphics_graphics2d_NManager.o : okj_easy_graphics_graphics2d_NManager.cpp
	${CC} ${JNIFLAGS} ${SOLUTIONFLAG} ${CFLAGS} okj_easy_graphics_graphics2d_NManager.cpp

okj_easy_graphics_graphics2d_NSpriteDef.o : okj_easy_graphics_graphics2d_NSpriteDef.cpp
	${CC} ${JNIFLAGS} ${SOLUTIONFLAG} ${CFLAGS} okj_easy_graphics_graphics2d_NSpriteDef.cpp
	
okj_easy_graphics_graphics2d_NativeSpriteBackend.o : okj_easy_graphics_graphics2d_NativeSpriteBackend.cpp
	${CC} ${JNIFLAGS} ${SOLUTIONFLAG} ${CFLAGS} okj_easy_graphics_graphics2d_NativeSpriteBackend.cpp

okj_easy_graphics_graphics2d_NWorld.o : okj_easy_graphics_graphics2d_NWorld.cpp
	${CC} ${JNIFLAGS} ${SOLUTIONFLAG} ${CFLAGS} okj_easy_graphics_graphics2d_NWorld.cpp

org_ege_utils_CollisionChecker.o : org_ege_utils_CollisionChecker.cpp
	${CC} ${JNIFLAGS} ${SOLUTIONFLAG} ${CFLAGS} org_ege_utils_CollisionChecker.cpp

org_ege_utils_SpriteUtils.o : org_ege_utils_SpriteUtils.cpp
	${CC} ${JNIFLAGS} ${SOLUTIONFLAG} ${CFLAGS} org_ege_utils_SpriteUtils.cpp

copy:
	cp *.dll C:/libs
rm:
	rm -f *.o *~