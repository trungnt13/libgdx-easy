CC = g++
CFLAGS = -c -Wall -O2 -mfpmath=sse -msse2 -fmessage-length=0 -m64
JNIFLAGS = -I"C:/Program Files/Java/jdk1.7.0_07/include" -I"C:/Program Files/Java/jdk1.7.0_07/include/win32"
DLLFLAGS = -Wl,--kill-at -shared -static-libgcc -static-libstdc++ -m64 -o
SOLUTIONDIR =  C:/1_VisualStudio2012/Pol2DEngine
SOLUTIONFLAG = -I"${SOLUTIONDIR}"

all: test.cpp
	${CC} ${SOLUTIONFLAG}  -o Test test.cpp

rm:
	rm -f *.o *~