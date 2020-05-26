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

// read a string from stdin protecting buffer overflow
#define GETS(B, S) {fgets(B,S-2,stdin);B[strlen(B)-1]=0;}

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
    int built_payload_size = 8 + payload.data_length;
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

int start_tcp_connection(char *target, char *porta) {
    int err, sock;
    struct addrinfo req, *list;

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
        perror("Falha ao tentar abrir socket.");
        freeaddrinfo(list);
        return -1;
    }
    if (connect(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
        freeaddrinfo(list);
        close(sock);
        return -1;
    }
    return sock;
}

int send_packet_tcp(Packet_tcp packet) {
    Built_Payload payload = build_payload(packet.payload);
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

    result.socket = socket;
    read(socket, &(result.payload.version), 1);
    read(socket, &(result.payload.code), 1);
    read(socket, &(result.payload.id), 2);
    read(socket, &(result.payload.data_length), 4);

    int success;
    if (result.payload.data_length == 0) {
        result.payload.data_length = 1;
        char *dummy = malloc(1);
        dummy[0] = 0;
        result.payload.data = dummy;
        success = 1;
    }
    else {
        result.payload.data = malloc(result.payload.data_length);
        success = read(socket, result.payload.data, result.payload.data_length);
    }

    if (success == -1) {
        result.socket = -1;
    }

    return result;
}

int handshake_tcp(int socket) {
    Packet_tcp packet;
    packet.socket = socket;
    packet.payload.version = CURRENT_PROTOCOL_VERSION;
    packet.payload.code = REQUEST_CODE_HELLO;
    packet.payload.id = id_maquina;
    packet.payload.data_length = 1;
    packet.payload.data = malloc(1);
    packet.payload.data[0] = 0;

    int resultado = send_packet_tcp(packet);
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

    int resultado = handshake_tcp(sock);
    if (resultado == REQUEST_CODE_ACK) {
        socket_sistema_central = sock;
        return sock;
    }

    return -1;
}

//Packet_udp send_packet_udp_and_receive_reply(Packet_udp packet) { NÃO TESTADO
//    Packet_udp resposta;
//    resposta.payload.code = REQUEST_CODE_FAILED_TO_SEND;
//
//    Built_Payload built_payload = build_payload(packet.payload);
//
//    struct sockaddr_storage serverAddr;
//    int sock, res, err;
//    unsigned int serverAddrLen;
//    struct addrinfo req, *list;
//
//    bzero((char *) &req, sizeof(req));
//
//// let getaddrinfo set the family depending on the supplied server address
//    req.ai_family = AF_UNSPEC;
//    req.ai_socktype = SOCK_DGRAM;
//    err = getaddrinfo(packet.endereco, packet.porta, &req, &list);
//    if (err) {
//        printf("Failed to get server address, error: %s\n", gai_strerror(err));
//        return resposta;
//    }
//    serverAddrLen = list->ai_addrlen;
//
//// store the server address for later use when sending requests
//    memcpy(&serverAddr, list->ai_addr, serverAddrLen);
//    freeaddrinfo(list);
//    bzero((char *) &req, sizeof(req));
//
//// for the local address, request the same family as determined for the server address
//    req.ai_family = serverAddr.ss_family;
//    req.ai_socktype = SOCK_DGRAM;
//    req.ai_flags = AI_PASSIVE; // local address
//    err = getaddrinfo(NULL, "0", &req, &list); // port 0 = auto assign
//    if (err) {
//        printf("Failed to get local address, error: %s\n", gai_strerror(err));
//        return resposta;
//    }
//    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
//    if (sock == -1) {
//        perror("Failed to open socket");
//        freeaddrinfo(list);
//        return resposta;
//    }
//    if (bind(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
//        perror("Failed to bind socket");
//        close(sock);
//        freeaddrinfo(list);
//        return resposta;
//    }
//
//    freeaddrinfo(list);
//
//    sendto(sock, built_payload.content, built_payload.size, 0, (struct sockaddr *) &serverAddr, serverAddrLen);
//
//    char *buffer = malloc(UDP_BUFFER_SIZE);
//
//    recvfrom(sock, buffer, UDP_BUFFER_SIZE, 0, (struct sockaddr *) &serverAddr, &serverAddrLen);
//    resposta.payload.version = buffer[0];
//    resposta.payload.code = buffer[1];
//    unsigned short *helper_short = &(buffer[2]);
//    resposta.payload.id = reverse_bytes_short(*helper_short);
//    unsigned int *helper_int = &(buffer[4]);
//    resposta.payload.data_length = reverse_bytes_int(*helper_int);
//
//    resposta.payload.data = malloc(resposta.payload.data_length);
//    strcpy(resposta.payload.data, buffer + 8);
//    free(buffer);
//
//    int len = strlen(packet.endereco) + 1;
//    packet.endereco[len-1] = 0;
//    resposta.endereco = malloc(len);
//    strcpy(resposta.endereco, packet.endereco);
//
//    len = strlen(packet.porta) + 1;
//    packet.porta[len-1] = 0;
//    resposta.porta = malloc(len);
//    strcpy(resposta.porta, packet.porta);
//
//    close(sock);
//
//    return resposta;
//}


