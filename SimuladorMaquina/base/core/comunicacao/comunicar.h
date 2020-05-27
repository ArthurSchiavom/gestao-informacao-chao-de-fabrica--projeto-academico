#ifndef SIMULADORMAQUINA_COMUNICAR_H
#define SIMULADORMAQUINA_COMUNICAR_H

#include "informacao_comunicacao.h"


/**
 *
 * @return (1) TRUE em caso de sucesso ou (2) -1 em caso de erro
 */
int send_packet_tcp(Packet_tcp packet);

/**
 *
 * @param socket
 * @return (1) O packet recebido em caso de sucesso ou (2) packet com socket definido como -1 em caso de falha
 */
Packet_tcp receive_packet_tcp(int socket);

/**
 * Handshake.
 *
 * @param target endereço do sistema alvo
 *
 * @return (1) socket aberto ou (2) NULL em caso de falha
 */
int start_tcp_connection(char *target, char *porta);

void close_tcp_connection(int socket);

/**
 *
 * @param socket
 * @return resposta do sistema alvo: (1) ACK em caso de sucesso ou (2) NACK em caso de falha
 */
int handshake_tcp(int socket);

int ligar_ao_servidor_central();



/**
 *
 * @return (1) TRUE em caso de sucesso ou (2) o código do erro (como REQUEST_CODE_NACK) em caso de erro
 */
Packet_udp send_packet_udp_and_receive_reply(Packet_udp packet);

/**
 *
 * @return (1) TRUE em caso de sucesso ou (2) o código do erro (como REQUEST_CODE_NACK) em caso de erro
 */
Packet_tcp receive_packet_udp(int socket);



#endif //SIMULADORMAQUINA_COMUNICAR_H