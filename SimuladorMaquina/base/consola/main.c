#include <stdio.h>
#include "../core/boot/boot.h"
#include "../core/comunicacao/scm/envio_mensagens_carregadas.h"
#include "../core/comunicacao/scm/rececao_packets.h"
#include <string.h>
#include <pthread.h>

#define INDEX_PARAM_ID_MAQUINA 1
#define INDEX_PARAM_INTERVALO_CADENCIA 2
#define INDEX_PARAM_CAMINHO_FICHEIRO_MENSAGENS 3
#define INDEX_PARAM_ENDERECO_SISTEMA_CENTRAL 4
#define INDEX_PARAM_ENDERECO_SMM 5
#define N_ARGS 6

int main(int argc, char **argv) {
    if (argc < N_ARGS) {
        printf("Algumentos necessários em falta. Os argumentos necessários (por ordem) são: ID da máquina, intervalo de cadência em segundos, "
               "caminho para o ficheiro de mensagens, endereço do sistema central e endereço do SMM.\n\n");
        return 1;
    }
    int success1, success2;

    printf("\n** A carregar\n");
    success1 = boot(argv[INDEX_PARAM_ID_MAQUINA], argv[INDEX_PARAM_INTERVALO_CADENCIA],
                    argv[INDEX_PARAM_CAMINHO_FICHEIRO_MENSAGENS],
                    argv[INDEX_PARAM_ENDERECO_SISTEMA_CENTRAL],
                    argv[INDEX_PARAM_ENDERECO_SMM]);

    if (success1 != TRUE) {
        printf("** Falha ao tentar conectar ao sistema central\n");
        return -1;
    }
    printf("** Simulador iniciado\n");

    pthread_t t1;
    success1 = iniciar_envio_mensagens(&t1);
    if (success1 == TRUE) {
        printf("** Sistema de envio de mensagens inicializado\n");
    } else {
        printf("Falha ao iniciar o envio de mensagens\n");
    }

    pthread_t t2;
    success2 = iniciar_rececao_packets(&t2);
    if (success2 == TRUE) {
        printf("** Sistema de receção de packets inicializado\n");
    } else {
        printf("Falha ao inicializar a receção de packets\n");
    }

    if (success1 == TRUE)
        pthread_join(t1, NULL);
    if (success2 == TRUE)
        pthread_join(t2, NULL);
    return 0;
}
