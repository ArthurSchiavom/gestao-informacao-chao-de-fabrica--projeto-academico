CC=gcc

PROGS=tls

all: $(PROGS)

tls: tls.c
	gcc -Wall tls.c -o tls -lssl -lcrypto

clean:
	rm -f $(PROGS)