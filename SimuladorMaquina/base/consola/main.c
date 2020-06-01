#include <stdio.h>
#include "../core/boot/boot.h"
#include "../core/comunicacao/envio_mensagens_carregadas.h"
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
