#ifndef SIMULADORMAQUINA_COMUNICAR_H
#define SIMULADORMAQUINA_COMUNICAR_H

#include "informacao_comunicacao.h"


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

#endif //SIMULADORMAQUINA_COMUNICAR_H
