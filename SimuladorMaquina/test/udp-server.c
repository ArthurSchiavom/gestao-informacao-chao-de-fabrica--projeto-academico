#include <strings.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

#define UDP_BUFFER_SIZE 512
#define SERVER_PORT "7194"

typedef struct Payload {
    unsigned char version;
    unsigned char code;
    unsigned short id;
    unsigned int data_length;
    char *data;
} Payload;

typedef struct Packet_tcp {
    Payload payload;
    int socket;
} Packet_tcp;

typedef struct Packet_udp {
    Payload payload;
    char *endereco;
    char *porta;
} Packet_udp;

typedef struct Built_Payload {
    int size;
    char *content;
} Built_Payload;

int reverse_bytes_int(int num) {
    int swapped = ((num >> 24) & 0xff) | // move byte 3 to byte 0
                  ((num << 8) & 0xff0000) | // move byte 1 to byte 2
                  ((num >> 8) & 0xff00) | // move byte 2 to byte 1
                  ((num << 24) & 0xff000000); // byte 0 to byte 3
    return swapped;
}

short reverse_bytes_short(short num) {
    short swapped = (num >> 8) | (num << 8);
    return swapped;
}

Built_Payload build_payload(Payload payload) {
    Built_Payload resultado;
    int built_payload_size = sizeof(payload) - 4 + payload.data_length;
    resultado.size = built_payload_size;

    resultado.content = malloc(built_payload_size);
    resultado.content[0] = payload.version;
    resultado.content[1] = payload.code;

    unsigned short id = reverse_bytes_short(payload.id);
    unsigned short *helper_short = &(resultado.content[2]);
    *helper_short = id;

    unsigned int data_length = reverse_bytes_int(payload.data_length);
    unsigned int *helper_int = &(resultado.content[4]);
    *helper_int = data_length;
    strcpy(resultado.content + 8, payload.data);


    return resultado;
}

int main(void) {
    struct sockaddr_storage client;
    int err, sock, sock2, res, i;
    unsigned int adl;
    struct addrinfo req, *list;


    struct addrinfo req2, *list2;
    unsigned int serverAddrLen;
    struct sockaddr_storage serverAddr;
    char cliIPtext[UDP_BUFFER_SIZE], cliPortText[UDP_BUFFER_SIZE];


    bzero((char *) &req, sizeof(req));
// request a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    req.ai_family = AF_INET6;
    req.ai_socktype = SOCK_DGRAM;
    req.ai_flags = AI_PASSIVE;

// Retorna um ou mais addrinfo structs
    err = getaddrinfo(NULL, SERVER_PORT, &req, &list);
    if (err) {
        printf("Failed to get local address, error: %s\n", gai_strerror(err));
        exit(1);
    }

//Cria um unbound socket e retorna um file descriptor
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1) {
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }

//Associa um endereco de socket a um socket identificado pelo descriptor que nao possui um endereco atribuido
    if (bind(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
        perror("Bind failed");
        close(sock);
        freeaddrinfo(list); //Liberta a memoria que foi alocada dinamincamente
        exit(1);
    }

    freeaddrinfo(list);

//Escreve uma string para o output
    puts("Listening for UDP requests (IPv6/IPv4). Use CTRL+C to terminate the server");
    adl = sizeof(client);

    Packet_udp resposta;
    char *buffer = malloc(UDP_BUFFER_SIZE);

    //Function shall receive a message from a connection-mode or connectionless-mode socket
    //Returna o tamanho da mensgaem em bytes,0 caso nao haja mensagens e -1 caso de erro.
    recvfrom(sock, buffer, UDP_BUFFER_SIZE, 0, (struct sockaddr *) &client, &adl);

    resposta.payload.version = buffer[0];
    resposta.payload.code = buffer[1];
    unsigned short *helper_short = &(buffer[2]);
    resposta.payload.id = reverse_bytes_short(*helper_short);
    unsigned int *helper_int = &(buffer[4]);
    resposta.payload.data_length = reverse_bytes_int(*helper_int);

    resposta.payload.data = malloc(resposta.payload.data_length);
    strcpy(resposta.payload.data, buffer + 8);

    printf("Received: %d, %d, %d, %d\n", resposta.payload.version, resposta.payload.code, resposta.payload.id,
           resposta.payload.data_length);

    if (!getnameinfo((struct sockaddr *) &client, adl,cliIPtext, UDP_BUFFER_SIZE, cliPortText, UDP_BUFFER_SIZE, NI_NUMERICHOST | NI_NUMERICSERV)) {
        printf("Request from node %s, port number %s\n", cliIPtext, cliPortText);

//        req2.ai_family = AF_UNSPEC;
//        req2.ai_socktype = SOCK_DGRAM;
//        err = getaddrinfo(cliIPtext, cliPortText, &req2, &list2);
//        if (err) {
//            printf("Failed to get server address, error: %s\n", gai_strerror(err));
//            return 1;
//        }
//        serverAddrLen = list2->ai_addrlen;
//
//// store the server address for later use when sending requests
//        memcpy(&serverAddr, list2->ai_addr, serverAddrLen);
//        freeaddrinfo(list2);
//        bzero((char *) &req2, sizeof(req2));
//
//// for the local address, request the same family as determined for the server address
//        req2.ai_family = serverAddr.ss_family;
//        req2.ai_socktype = SOCK_DGRAM;
//        req2.ai_flags = AI_PASSIVE; // local address
//        err = getaddrinfo(NULL, "0", &req2, &list2); // port 0 = auto assign
//        if (err) {
//            printf("Failed to get local address, error: %s\n", gai_strerror(err));
//            return 1;
//        }
//        sock2 = socket(list2->ai_family, list2->ai_socktype, list2->ai_protocol);
//        if (sock2 == -1) {
//            perror("Failed to open socket");
//            freeaddrinfo(list);
//            return 1;
//        }
//        if (bind(sock2, (struct sockaddr *) list2->ai_addr, list2->ai_addrlen) == -1) {
//            perror("Failed to bind socket");
//            close(sock2);
//            freeaddrinfo(list);
//            return 1;
//        }

        freeaddrinfo(list);
        resposta.payload.code = 10;
        Built_Payload built_payload = build_payload(resposta.payload);
        sendto(sock, built_payload.content, built_payload.size, 0, (struct sockaddr *) &serverAddr, serverAddrLen);
    } else printf("Got request, but failed to get client address\n");


    free(buffer);
    close(sock2);
    return 0;
}
