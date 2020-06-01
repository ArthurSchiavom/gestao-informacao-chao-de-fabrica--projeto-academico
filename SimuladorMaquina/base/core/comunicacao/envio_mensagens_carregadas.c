#include <sys/un.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "envio_mensagens_carregadas.h"
#include "informacao_comunicacao.h"
#include "../mensagem/carregamento_mensagem.h"
#include "../maquina/informacao_maquina.h"
#include "comunicar.h"
#include "../utils/const.h"

void reconectar_sistema_central() {
    printf("Falha ao tentar contactar o servidor central, a tentar reconectar.");

    int success = FALSE;
    int should_sleep = FALSE;
    while (success != TRUE) {
        printf("\n");
        Payload resultado;
        success = handshake_tcp(endereco_sistema_central, PORTA_SISTEMA_CENTRAL, &resultado);
        if (success == FALSE) {
            printf("Servidor central não alcançado. Próxima tentativa em %d segundos.\n", TEMPO_ESPERA_RECONEXAO_SCM_SEGUNDOS);
            should_sleep = TRUE;
        }

        if (success == TRUE) {
            if (resultado.code == REQUEST_CODE_NACK) {
                printf("O servidor central recusou a tentativa de conexão por não reconhecer a máquina.");
                if (resultado.data[0] != 0) {
                    printf(": %s", resultado.data);
                }
                printf(". Próxima tentativa em %d segundos.\n", TEMPO_ESPERA_RECONEXAO_SCM_SEGUNDOS);
                should_sleep = TRUE;
            }
            else if (resultado.code != REQUEST_CODE_ACK) {
                printf("O servidor central recusou a tentativa de conexão por motivos desconhecidos. Próxima tentativa em %d segundos.\n", TEMPO_ESPERA_RECONEXAO_SCM_SEGUNDOS);
                should_sleep = TRUE;
            }
            else {
                printf("Reconectado ao sistema central.\n");
                success = TRUE;
            }

            free(resultado.data);
        }

        if (should_sleep) {
            sleep(TEMPO_ESPERA_RECONEXAO_SCM_SEGUNDOS);
        }
    }
}

void tentar_enviar_mensagem(Payload payload) {
    short limpar_data_resultado;
    int sucesso;

    Payload resultado;
    resultado.code = REQUEST_CODE__INTERNO_SEM_SIGNIFICADO;
    resultado.version = -1;
    while (resultado.code != REQUEST_CODE_ACK) {
        limpar_data_resultado = TRUE;
        sucesso = enviar_packet_tcp(endereco_sistema_central, PORTA_SISTEMA_CENTRAL, payload, &resultado, TRUE);
        if (sucesso == FALSE || resultado.code != REQUEST_CODE_ACK) {
            limpar_data_resultado = FALSE;
            reconectar_sistema_central();
            resultado.code = REQUEST_CODE__INTERNO_SEM_SIGNIFICADO;
        }

        if (limpar_data_resultado) {
            free(resultado.data);
        }
    }
}

_Noreturn void modulo_envio_mensagens() {
    Payload payload;
    payload.version = CURRENT_PROTOCOL_VERSION;
    payload.code = REQUEST_CODE_MSG;
    payload.id = id_maquina;

    int tamanho_mensagem;
    char *message = malloc(1);

    while (1) {
        if (*(proxima_mensagem()) == 0) {
            sleep(3); // Espera pela geração de novas mensagens. Caso tal seja implementado no futuro, no caso qual basta alterar o método proxima_mensagem()
        } else {
            tamanho_mensagem = (int) strlen(mensagem_atual()) + 1;
            message = realloc(message, tamanho_mensagem);
            strcpy(message, mensagem_atual());
            payload.data_length = tamanho_mensagem;
            payload.data = message;

            tentar_enviar_mensagem(payload);
        }
        sleep(intervalo_entre_mensagens_segundos);
    }

//    free(message); // uncomment if this method is ever changed to end
}

int iniciar_envio_mensagens(pthread_t *t) {
    Payload payload;
    int sucesso = handshake_tcp(endereco_sistema_central, PORTA_SISTEMA_CENTRAL, &payload);
    if (sucesso == TRUE)
        free(payload.data);

    int success = pthread_create(t, NULL, modulo_envio_mensagens, NULL);
    if (success != 0) {
        return -1;
    }

    return 0;
}
