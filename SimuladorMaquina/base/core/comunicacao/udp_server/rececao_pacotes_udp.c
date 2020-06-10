#include "rececao_pacotes_udp.h"
#include "../../comunicacao/comunicar.h"
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <netdb.h>
#include <string.h>

#define UDP_BUFFER_SIZE 512


int reverse_bytes_int(int num) {
    int swapped = ((num >> 24) & 0xff) | // move byte 3 to byte 0
                  ((num << 8) & 0xff0000) | // move byte 1 to byte 2
                  ((num >> 8) & 0xff00) | // move byte 2 to byte 1
                  ((num << 24) & 0xff000000); // byte 0 to byte 3
    return swapped;
}

Built_Payload build_payload_udp(Payload payload) {
    Built_Payload resultado;
    short built_payload_size = 6 + payload.data_length;
    resultado.size = built_payload_size;

    resultado.content = malloc(built_payload_size);
    resultado.content[0] = payload.version;
    resultado.content[1] = payload.code;

    unsigned short id = reverse_bytes_short(payload.id);
    unsigned short *helper_short = &(resultado.content[2]);
    *helper_short = id;

    unsigned short data_length =payload.data_length;
    unsigned int *helper_int = &(resultado.content[4]);
    *helper_int = data_length;
    strcpy(resultado.content + 6, payload.data);


    return resultado;
}

Packet_udp processamento_de_pedido_udp(unsigned char code){
    Packet_udp reply;
    int tamanho= strlen(id_linha_producao)+1;
    unsigned short *helper_short = &id_maquina;
    unsigned short *helper_int = &tamanho;
    switch (code) {
        case REQUEST_CODE_HELLO:
            reply.payload.data=malloc(strlen(id_linha_producao)+1);
            reply.payload.version=CURRENT_PROTOCOL_VERSION;
            reply.payload.code=ultimo_estado_pedido;
            reply.payload.id= reverse_bytes_short(*helper_short);
            strcpy(reply.payload.data, id_linha_producao);
            reply.payload.data_length=*helper_int;
            return reply;
        case REQUEST_CODE_RESET:
        default:
            printf("Codigo nao permitido!");
    }
}

_Noreturn void start_udp_server() {
    struct sockaddr_storage client;
    int err, sock;
    unsigned int adl;
    struct addrinfo req, *list;
    char cliIPtext[UDP_BUFFER_SIZE], cliPortText[UDP_BUFFER_SIZE];


    bzero((char *) &req, sizeof(req));
// request a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    req.ai_family = AF_INET6;
    req.ai_socktype = SOCK_DGRAM;
    req.ai_flags = AI_PASSIVE;

// Retorna um ou mais addrinfo structs
    err = getaddrinfo(NULL,PORTA_SMM, &req, &list);
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
    adl = sizeof(client);
    Packet_udp resposta;

//Function shall receive a message from a connection-mode or connectionless-mode socket
//Returna o tamanho da mensagem em bytes,0 caso nao haja mensagens e -1 caso de erro.
//Receber pacote:
    while(1) {
        char *buffer = malloc(UDP_BUFFER_SIZE);
        recvfrom(sock, buffer, UDP_BUFFER_SIZE, 0, (struct sockaddr *) &client, &adl);
        resposta.payload.version = buffer[0];
        resposta.payload.code = buffer[1];
        unsigned short *helper_short = &(buffer[2]);
        resposta.payload.id = reverse_bytes_short(*helper_short);
        unsigned int *helper_int = &(buffer[4]);
        resposta.payload.data_length = reverse_bytes_int(*helper_int);
        resposta.payload.data = malloc(resposta.payload.data_length);
        strcpy(resposta.payload.data, buffer + 8);

        printf("Pedido recebido: %d, %d, %d, %d\n", resposta.payload.version, resposta.payload.code, resposta.payload.id,resposta.payload.data_length);

        Packet_udp reply=processamento_de_pedido_udp(resposta.payload.code);
        //Esta funcao faz a traducao do endereco do socket para um nome de um no e um servico de localizacao
        if (!getnameinfo((struct sockaddr *) &client, adl,cliIPtext, UDP_BUFFER_SIZE, cliPortText, UDP_BUFFER_SIZE, NI_NUMERICHOST | NI_NUMERICSERV)){
            printf("Request from node %s, port number %s\n", cliIPtext, cliPortText);
            Built_Payload built_payload = build_payload_udp(reply.payload);
            int value = sendto(sock, built_payload.content, built_payload.size, 0, (struct sockaddr *) &client, adl);
            if (value < 0)
                printf("Falha ao enviar o reply");
        } else printf("Got request, but failed to get client address\n");
        free(buffer);
    }
}



int iniciar_servidor_udp(pthread_t *t) {
    int success = pthread_create(t, NULL,start_udp_server, NULL);
    if (success != 0) {
        return FALSE;
    }
    return TRUE;
}

