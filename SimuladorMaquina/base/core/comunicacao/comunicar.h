#ifndef SIMULADORMAQUINA_COMUNICAR_H
#define SIMULADORMAQUINA_COMUNICAR_H

#include "informacao_comunicacao.h"


/**
 * @return (1) o socket em caso de sucesso ou (2) -1 aberto caso contrário
 */
int start_tcp_connection(char *target, char *porta);

/**
 * @return (1) -1 em caso de não conseguir contactar o servidor ou (2) o código de resposta do servidor caso contrário
 */
int handshake_servidor_central();

/**
 * @return TRUE em caso de sucesso e FALSE caso contrário
 */
int enviar_packet_tcp(char *target, char *porta, Payload payload, Payload *resultado, short inverterBytesDeNumeros);

#endif //SIMULADORMAQUINA_COMUNICAR_H
