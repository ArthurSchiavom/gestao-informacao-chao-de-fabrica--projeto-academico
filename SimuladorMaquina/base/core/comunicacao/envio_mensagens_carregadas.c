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

_Noreturn void modulo_envio_mensagens() {
    Payload payload;
    payload.version = CURRENT_PROTOCOL_VERSION;
    payload.code = REQUEST_CODE_MSG;
    payload.id = id_maquina;

    int tamanho_mensagem;
    char *message = malloc(1);

    short limpar_data_resultado;
    int sucesso;
    while (1) {
        if (*(proxima_mensagem()) == 0) {
            sleep(3); // Espera pela geração de novas mensagens. Caso tal seja implementado no futuro, no caso qual basta alterar o método proxima_mensagem()
        } else {
            tamanho_mensagem = (int) strlen(mensagem_atual()) + 1;
            message = realloc(message, tamanho_mensagem);
            strcpy(message, mensagem_atual());
            payload.data_length = tamanho_mensagem;
            payload.data = message;

            Payload resultado;
            resultado.code = REQUEST_CODE__INTERNO_SEM_SIGNIFICADO;
            resultado.version = -1;
            short desconectado = FALSE;
            while (resultado.code != REQUEST_CODE_ACK) {
                limpar_data_resultado = TRUE;
                sucesso = enviar_packet_tcp(endereco_sistema_central, PORTA_SISTEMA_CENTRAL, payload, &resultado, TRUE);
                if (sucesso == FALSE) {
                    limpar_data_resultado = FALSE;
                }

                if (sucesso == FALSE || resultado.code != REQUEST_CODE_ACK) {
                    desconectado = TRUE;
                    printf("Desconectado do sistema central - tentativa de reconexão em 3s\n");
                    if (resultado.code == REQUEST_CODE_NACK) {
                        resultado.data = realloc(resultado.data, resultado.data_length + 1);
                        resultado.data[resultado.data_length] = 0; // caso não tenha sido enviada com um zero no final
                        printf("Mensagem do sistema central: %s\n", resultado.data);
                    }

                    sleep(TEMPO_RECONEXAO_SCM_SEGUNDOS);

                    sucesso = handshake_servidor_central();
                    if (sucesso == -1) {
                        resultado.code = REQUEST_CODE__INTERNO_SEM_SIGNIFICADO;
                    }
                    else {
                        resultado.code = sucesso;
                    }
                } else {
                    if (desconectado) {
                        desconectado = FALSE;
                        printf("Reconectado ao sistema central\n\n");
                    }
                }

                if (limpar_data_resultado) {
                    free(resultado.data);
                }
            }
        }
        sleep(intervalo_entre_mensagens_segundos);
    }

//    free(message); // uncomment if this method is ever changed to end
}

int iniciar_envio_mensagens(pthread_t *t) {
    int success = pthread_create(t, NULL, modulo_envio_mensagens, NULL);
    if (success != 0) {
        return -1;
    }

    return 0;
}
