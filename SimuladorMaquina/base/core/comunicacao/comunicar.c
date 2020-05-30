#include "comunicar.h"
#include "../utils/const.h"
#include "../maquina/informacao_maquina.h"
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <errno.h>

#define UDP_BUFFER_SIZE 512
#define TIMEOUT_SECONDS 3

// read a string from stdin protecting buffer overflow
#define GETS(B, S) {fgets(B,S-2,stdin);B[strlen(B)-1]=0;}

typedef struct Built_Payload {
    int size;
    char *content;
} Built_Payload;

//int reverse_bytes_int(int num) {
//    int swapped = ((num >> 24) & 0xff) | // move byte 3 to byte 0
//                  ((num << 8) & 0xff0000) | // move byte 1 to byte 2
//                  ((num >> 8) & 0xff00) | // move byte 2 to byte 1
//                  ((num << 24) & 0xff000000); // byte 0 to byte 3
//    return swapped;
//}

short reverse_bytes_short(short num) {
    short swapped = (num >> 8) | (num << 8);
    return swapped;
}

Built_Payload build_payload(Payload payload, short inverterBytesDeNumeros) {
    Built_Payload resultado;
    int built_payload_size = PAYLOAD_STATIC_DATA_SIZE + payload.data_length;
    resultado.size = built_payload_size;

    resultado.content = malloc(built_payload_size);
    resultado.content[0] = payload.version;
    resultado.content[1] = payload.code;

    unsigned short id = payload.id;
    if (inverterBytesDeNumeros == TRUE) {
        id = reverse_bytes_short(id);
    }
    unsigned short *helper_short = &(resultado.content[2]);
    *helper_short = id;

    unsigned short data_length = payload.data_length;
    if (inverterBytesDeNumeros == TRUE) {
        data_length = reverse_bytes_short(data_length);
    }
    helper_short = &(resultado.content[4]);
    *helper_short = data_length;
    if (data_length > 0) {
        strcpy(resultado.content + PAYLOAD_STATIC_DATA_SIZE, payload.data);
    }


    return resultado;
}

struct timeval to;

int start_tcp_connection(char *target, char *porta) {
    int err, sock;
    struct addrinfo req, *list;

    to.tv_sec = TIMEOUT_SECONDS;
    to.tv_usec = 0;
    setsockopt (sock,SOL_SOCKET,SO_RCVTIMEO,(char *)&to, sizeof(to));

    bzero((char *) &req, sizeof(req));
    // let getaddrinfo set the family depending on the supplied server address
    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;
    err = getaddrinfo(target, porta, &req, &list);
    if (err) {
        printf("Falha ao tentar obter endereço IP: %s\n", gai_strerror(err));
        return -1;
    }
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1) {
        perror("Falha ao tentar abrir socket");
        freeaddrinfo(list);
        return -1;
    }
    if (connect(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
        perror("Falha ao tentar conectar ao socket");
        freeaddrinfo(list);
        close(sock);
        return -1;
    }
    return sock;
}

int send_packet_tcp(Packet_tcp packet, short inverterBytesDeNumeros) {
    Built_Payload payload = build_payload(packet.payload, inverterBytesDeNumeros);
    int success = write(packet.socket, payload.content, payload.size);

    if (success == -1) {
        return -1;
    }
    return TRUE;
}


void close_tcp_connection(int socket) {
    close(socket);
}

Packet_tcp receive_packet_tcp(int socket) {
    Packet_tcp result;

    Payload received_payload;

    received_payload.version = 0;
    received_payload.code = 0;
    read(socket, &(received_payload.version), 1);
    read(socket, &(received_payload.code), 1);
    read(socket, &(received_payload.id), 2);
    read(socket, &(received_payload.data_length), 2);
    received_payload.id = received_payload.id;
    received_payload.data_length = received_payload.data_length;

    int success;
    if (received_payload.data_length == 0) {
        received_payload.data_length = 1;
        char *dummy = malloc(1);
        dummy[0] = 0;
        received_payload.data = dummy;
        success = 1;
    } else {
        received_payload.data = malloc(received_payload.data_length);
        read(socket, received_payload.data, received_payload.data_length);
    }

    if (success == -1) {
        result.socket = -1;
    }

    result.payload = received_payload;
    result.socket = socket;
    
    return result;
}

int handshake_tcp(int socket, short inverterBytesDeNumeros) {
    Packet_tcp packet;
    packet.socket = socket;
    packet.payload.version = CURRENT_PROTOCOL_VERSION;
    packet.payload.code = REQUEST_CODE_HELLO;
    packet.payload.id = id_maquina;
    packet.payload.data_length = 0;

    int resultado = send_packet_tcp(packet, inverterBytesDeNumeros);
    if (resultado == -1) {
        return -1;
    }
    Packet_tcp reply = receive_packet_tcp(socket);

    if (reply.socket == -1) {
        return -1;
    }

    return reply.payload.code;
}

int ligar_ao_servidor_central() {
    close(socket_sistema_central);
    socket_sistema_central = -1;
    int sock = start_tcp_connection(endereco_sistema_central, PORTA_SISTEMA_CENTRAL);
    if (sock == -1) {
        return -1;
    }

    int resultado = handshake_tcp(sock, TRUE);
    if (resultado == REQUEST_CODE_ACK) {
        socket_sistema_central = sock;
        return sock;
    }

    return -1;
}


