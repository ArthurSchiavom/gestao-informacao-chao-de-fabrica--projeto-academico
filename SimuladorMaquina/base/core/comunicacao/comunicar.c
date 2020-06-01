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

short reverse_bytes_short(short num) {
    short swapped = ((num<<8)&0xff00) | ((num>>8)&0x00ff);
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
    setsockopt (sock,SOL_SOCKET,SO_SNDTIMEO,(char *)&to, sizeof(to));

    bzero((char *) &req, sizeof(req));
    // let getaddrinfo set the family depending on the supplied server address
    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;
    err = getaddrinfo(target, porta, &req, &list);
    if (err) {
        return -1;
    }
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1) {
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

/**
 * @return TRUE em caso de sucesso e FALSE caso contrário
 */
int send_packet_tcp_on_open_socket(Packet_tcp packet, short inverterBytesDeNumeros) {
    Built_Payload payload = build_payload(packet.payload, inverterBytesDeNumeros);
    int success = write(packet.socket, payload.content, payload.size);

    if (success == -1) {
        return FALSE;
    }
    return TRUE;
}

/**
 * @return TRUE em caso de sucesso e FALSE caso contrário
 */
int receive_packet_tcp_on_open_socket(int socket, Payload *resultado) {
    resultado->version = 0;
    resultado->code = 0;
    int sucesso = read(socket, &(resultado->version), 1);
    if (sucesso == -1)
        return FALSE;
    sucesso = read(socket, &(resultado->code), 1);
    if (sucesso == -1)
        return FALSE;
    sucesso = read(socket, &(resultado->id), 2);
    if (sucesso == -1)
        return FALSE;
    sucesso = read(socket, &(resultado->data_length), 2);
    if (sucesso == -1)
        return FALSE;
    resultado->id = reverse_bytes_short(resultado->id);
    resultado->data_length = reverse_bytes_short(resultado->data_length);

    if (resultado->data_length == 0) {
        resultado->data_length = 1;
        char *dummy = malloc(resultado->data_length);
        dummy[0] = 0;
        resultado->data = dummy;
    } else {
        resultado->data = malloc(resultado->data_length);
        sucesso = read(socket, resultado->data, resultado->data_length);
        if (sucesso == -1) {
            free(resultado->data);
            return FALSE;
        }
    }

    return TRUE;
}

/**
 * @return (1) FALSE em caso de não conseguir contactar o servidor ou (2) o código de resposta do servidor caso contrário
 */
int handshake_tcp_socket_aberto(int socket, Payload *resultado, short inverterBytesDeNumeros) {
    Packet_tcp packet;
    packet.socket = socket;
    packet.payload.version = CURRENT_PROTOCOL_VERSION;
    packet.payload.code = REQUEST_CODE_HELLO;
    packet.payload.id = id_maquina;
    packet.payload.data_length = 0;

    int sucesso = send_packet_tcp_on_open_socket(packet, inverterBytesDeNumeros);
    if (sucesso == FALSE) {
        return -1;
    }

    int reply = receive_packet_tcp_on_open_socket(socket, resultado);

    return reply;
}

int handshake_tcp(char *endereco, char *porta, Payload *resultado) {
    int sock = start_tcp_connection(endereco, porta);
    if (sock == -1) {
        return FALSE;
    }

    int sucesso = handshake_tcp_socket_aberto(sock, resultado, TRUE);

    close(sock);
    return sucesso;
}

/**
 * @return TRUE em caso de sucesso e FALSE caso contrário. No primeiro caso, o payload estará preenchido com a resposta do servidor
 * e o campo data deve ser sempre manualmente libertado da memória.
 */
int enviar_packet_tcp(char *target, char *porta, Payload payload, Payload *resultado, short inverterBytesDeNumeros) {
    int sock = start_tcp_connection(target, porta);
    if (sock == -1) {
        return FALSE;
    }

    Packet_tcp packet;
    packet.payload = payload;
    packet.socket = sock;

    int sucesso = send_packet_tcp_on_open_socket(packet, inverterBytesDeNumeros);

    if (sucesso == TRUE) {
        sucesso = receive_packet_tcp_on_open_socket(sock, resultado);
    }

    close(sock);
    return sucesso;
}


