CC = x86_64-w64-mingw32-g++
CFLAGS = -c -Wall -O2 -mfpmath=sse -msse2 -fmessage-length=0 -m64
JNIFLAGS = -I"C:/Program Files/Java/jdk1.7.0_06/include" -I"C:/Program Files/Java/jdk1.7.0_06/include/win32"
DLLFLAGS = -Wl,--kill-at -shared -static-libgcc -static-libstdc++ -m64 -o
	
all: libenative clean

libenative: org_ege_utils_CollisionChecker.o org_ege_utils_SpriteUtils.o SpriteProcessor.o utils.o CollisionProcessor.o Array.o GridSimulation.o eMath.o Vector2.o
	${CC} ${DLLFLAGS} libenative.dll org_ege_utils_CollisionChecker.o org_ege_utils_SpriteUtils.o SpriteProcessor.o utils.o CollisionProcessor.o Array.o GridSimulation.o eMath.o Vector2.o

Array.o: utils/Array.cpp 
	${CC} ${CFLAGS} utils/Array.cpp 

GridSimulation.o: utils/GridSimulation.cpp 
	${CC} ${CFLAGS} utils/GridSimulation.cpp

eMath.o: utils/math/eMath.cpp 
	${CC} ${CFLAGS} utils/math/eMath.cpp
	
Vector2.o: utils/math/Vector2.cpp
	${CC} ${CFLAGS} utils/math/Vector2.cpp
	
CollisionProcessor.o: CollisionProcessor.cpp 
	${CC} ${CFLAGS} CollisionProcessor.cpp

SpriteProcessor.o : SpriteProcessor.cpp
	${CC} ${CFLAGS} SpriteProcessor.cpp
	
utils.o: utils.cpp 
	${CC} ${CFLAGS} utils.cpp 
	
org_ege_utils_CollisionChecker.o : org_ege_utils_CollisionChecker.cpp 
	${CC} ${JNIFLAGS} ${CFLAGS} org_ege_utils_CollisionChecker.cpp

org_ege_utils_SpriteUtils.o: org_ege_utils_SpriteUtils.cpp
	${CC} ${JNIFLAGS} ${CFLAGS} org_ege_utils_SpriteUtils.cpp
	
clean:
	rm -f *.o *~

