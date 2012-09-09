CFLAGS = -c -Wall
CC = x86_64-w64-mingw32-gcc 
LIBS =  -lm 

all: libenative.dll

scanner: scanner.o reader.o charcode.o token.o error.o
	${CC} scanner.o reader.o charcode.o token.o error.o -o scanner

reader.o: reader.c
	${CC} ${CFLAGS} reader.c

scanner.o: scanner.c
	${CC} ${CFLAGS} scanner.c

charcode.o: charcode.c
	${CC} ${CFLAGS} charcode.c

token.o: token.c
	${CC} ${CFLAGS} token.c

error.o: error.c
	${CC} ${CFLAGS} error.c

clean:
	rm -f *.o *~

