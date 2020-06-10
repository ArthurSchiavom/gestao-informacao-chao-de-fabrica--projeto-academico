#ifndef SIMULADORMAQUINA_COMUNICAR_H
#define SIMULADORMAQUINA_COMUNICAR_H

#include "informacao_comunicacao.h"
#include "../utils/const.h"
#include "../maquina/informacao_maquina.h"
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>


typedef struct Built_Payload {
    int size;
    char *content;
} Built_Payload;


/**
 * @return (1) o socket em caso de sucesso ou (2) -1 aberto caso contrário
 */
int start_tcp_connection(char *target, char *porta);

/**
 * @return (1) FALSE em caso de não conseguir contactar o servidor ou (2) TRUE caso contrário.
 * No primeiro caso o payload não terá informação válida. No segundo caso o payload estará preenchido com a resposta do servidor
 * e o campo data deve ser sempre manualmente libertado da memória.
 */
int handshake_tcp(char *endereco, char *porta, Payload *resultado);

/**
 * @return TRUE em caso de sucesso e FALSE caso contrário
 */
int enviar_packet_tcp(char *target, char *porta, Payload payload, Payload *resultado, short inverterBytesDeNumeros);

int receive_packet_tcp_on_open_socket(int socket, Payload *resultado);

int send_packet_tcp_on_open_socket(Packet_tcp packet, short inverterBytesDeNumeros);

Built_Payload build_payload(Payload payload, short inverterBytesDeNumeros);

short reverse_bytes_short(short num);

#endif //SIMULADORMAQUINA_COMUNICAR_H
