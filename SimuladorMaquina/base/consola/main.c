#include <stdio.h>
#include "../core/comunicacao/informacao_comunicacao.h"
#include "../core/boot/boot.h"
#include "../core/comunicacao/comunicar.h"
#include "../core/comunicacao/envio_mensagens_carregadas.h"
#include "../core/utils/const.h"
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

#define INDEX_PARAM_ID_MAQUINA 1
#define INDEX_PARAM_INTERVALO_CADENCIA 2
#define INDEX_PARAM_CAMINHO_FICHEIRO_MENSAGENS 3
#define INDEX_PARAM_ENDERECO_SISTEMA_CENTRAL 4
#define INDEX_PARAM_ENDERECO_SMM 5
#define N_ARGS 6

// labs-ssh5.dei.isep.ipp.pt

void test_packet_transfer() {
    printf("starting\n");
    int socket = start_tcp_connection("labs-ssh5.dei.isep.ipp.pt", PORTA_SISTEMA_CENTRAL);
    printf("Socket: %d\n", socket);
    printf("started\n");
    Packet_tcp packet;
    packet.socket = socket;
    packet.payload.version = CURRENT_PROTOCOL_VERSION;
    packet.payload.code = REQUEST_CODE_HELLO;
    packet.payload.id = 123;
    packet.payload.data_length = 7;
    packet.payload.data = malloc(7);
    strcpy(packet.payload.data, "HELLO!");
    send_packet_tcp(packet, TRUE);
    close_tcp_connection(socket);
    printf("ended\n");
}

void test_handshake() {
    printf("A iniciar conexão\n");
    int socket = start_tcp_connection("labs-ssh5.dei.isep.ipp.pt", PORTA_SISTEMA_CENTRAL);
    printf("Conexão completa, handshake_tcp a começar\n");
    int resultado = handshake_tcp(socket, TRUE);
    printf("Resultado: %d\n", resultado);
    close_tcp_connection(socket);
}

int main(int argc, char **argv) {
    if (argc < N_ARGS) {
        printf("Algumentos necessários em falta. Os argumentos necessários (por ordem) são: ID da máquina, intervalo de cadência em segundos, "
               "caminho para o ficheiro de mensagens, endereço do sistema central e endereço do SMM.\n\n");
        return 1;
    }

    printf("\n** A carregar\n");
    int success = boot(argv[INDEX_PARAM_ID_MAQUINA], argv[INDEX_PARAM_INTERVALO_CADENCIA],
            argv[INDEX_PARAM_CAMINHO_FICHEIRO_MENSAGENS],
            argv[INDEX_PARAM_ENDERECO_SISTEMA_CENTRAL],
            argv[INDEX_PARAM_ENDERECO_SMM]);

    if (success == -1) {
        printf("** Falha ao tentar conectar ao sistema central\n");
    }
    printf("** Simulador iniciado\n");

    pthread_t t1;
    success = iniciar_envio_mensagens(&t1);
    if (success == -1) {
        printf("Falha ao iniciar a thread de envio de mensagens");
    } else {
        printf("** Sistema de envio de mensagens inicializado\n");
        pthread_join(t1, NULL);
    }

    return 0;
}
