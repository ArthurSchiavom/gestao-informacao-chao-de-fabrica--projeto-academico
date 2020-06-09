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
#include <fcntl.h>
#include <errno.h>

#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>
#include <openssl/ossl_typ.h>


/**
 * DEPRECATED
 *
 * @return (1) o socket em caso de sucesso ou (2) -1 aberto caso contrário
 */
int start_tcp_connection(char *target, char *porta);

/**
 * DEPRECATED
 *
 * @return (1) FALSE em caso de não conseguir contactar o servidor ou (2) TRUE caso contrário.
 * No primeiro caso o payload não terá informação válida. No segundo caso o payload estará preenchido com a resposta do servidor
 * e o campo data deve ser sempre manualmente libertado da memória.
 */
int handshake_tcp(char *endereco, char *porta, Payload *resultado);

/**
 * DEPRECATED
 *
 * @return TRUE em caso de sucesso e FALSE caso contrário. No primeiro caso, o payload estará preenchido com a resposta do servidor
 * e o campo data deve ser sempre manualmente libertado da memória.
 */
int enviar_packet_tcp(char *target, char *porta, Payload payload, Payload *resultado, short inverterBytesDeNumeros);

/**
 * DEPRECATED
 *
 * @return (1) TRUE em caso de sucesso ou (2) FALSE em caso de erro. Em caso de sucesso, o campo data deve sempre ser manualmente libertado.
 */
int receive_packet_tcp_on_open_socket(int socket, Payload *resultado);

/**
 * DEPRECATED
 *
 * @return TRUE em caso de sucesso e FALSE caso contrário
 */
int send_packet_tcp_on_open_socket(Packet_tcp packet, short inverterBytesDeNumeros);




/**
 * @return a conexão aberta em caso de sucesso ou NULL em caso de erro.
 */
SSL* start_tls_tcp_connection(char *target, char *porta, int *sock);

/**
 * @return (1) TRUE em caso de sucesso ou (2) FALSE em caso de erro
 */
int send_packet_tcp_on_open_ssl_con(Packet_ssl packet, short inverterBytesDeNumeros);

/**
 * @return TRUE em caso de sucesso e FALSE caso contrário. Em caso de sucesso, o campo data deve sempre ser manualmente libertado.
 */
int receive_packet_tcp_on_open_ssl_con(SSL *con, Payload *resultado);

/**
 * @return (1) TRUE em caso de sucesso ou (2) FALSE em caso de erro
 */
int handshake_ssl(char *target, char *porta, Payload *resultado, short inverterBytesDeNumeros);

/**
 * @return TRUE em caso de sucesso e FALSE caso contrário. No primeiro caso, o payload estará preenchido com a resposta do servidor
 * e o campo data deve ser sempre manualmente libertado da memória.
 */
int enviar_packet_tcp_tls(char *target, char *porta, Payload payload, Payload *resultado, short inverterBytesDeNumeros);

#endif //SIMULADORMAQUINA_COMUNICAR_H
