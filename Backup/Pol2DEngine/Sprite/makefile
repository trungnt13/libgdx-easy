CC = x86_64-w64-mingw32-g++
CFLAGS = -c -Wall -O2 -mfpmath=sse -msse2 -fmessage-length=0 -m64
JNIFLAGS = -I"C:/Program Files/Java/jdk1.7.0_07/include" -I"C:/Program Files/Java/jdk1.7.0_07/include/win32"
DLLFLAGS = -Wl,--kill-at -shared -static-libgcc -static-libstdc++ -m64 -o
SOLUTIONDIR =  C:/1_VisualStudio2012/Pol2DEngine
SOLUTIONFLAG = -I"${SOLUTIONDIR}"

all: sprite moving rm

sprite: *.cpp
	${CC} ${CFLAGS} ${SOLUTIONFLAG} *.cpp

moving:
	mv *.o ${SOLUTIONDIR}/Pol2D/

rm:
	rm -f *.o *~