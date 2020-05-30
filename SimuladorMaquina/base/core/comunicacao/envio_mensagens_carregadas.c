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
    Packet_tcp packet;
    packet.payload.version = CURRENT_PROTOCOL_VERSION;
    packet.payload.code = REQUEST_CODE_MSG;
    packet.payload.id = id_maquina;

    int tamanho_mensagem;
    char *message = malloc(1);

    short desconectado = FALSE;
    short limpar_data_resultado;
    while (1) {
        if (*(proxima_mensagem()) == 0) {
            sleep(3); // Espera pela geração de novas mensagens. Caso tal seja implementado no futuro, no caso qual basta alterar o método proxima_mensagem()
        } else {
            packet.socket = socket_sistema_central;
            tamanho_mensagem = (int) strlen(mensagem_atual()) + 1;
            message = realloc(message, tamanho_mensagem);
            strcpy(message, mensagem_atual());
            packet.payload.data_length = tamanho_mensagem;
            packet.payload.data = message;

            Packet_tcp resultado;
            resultado.payload.code = REQUEST_CODE__INTERNO_SEM_SIGNIFICADO;
            resultado.socket = -1;
            while (resultado.payload.code != REQUEST_CODE_ACK) {
                limpar_data_resultado = FALSE;
                int sucesso = send_packet_tcp(packet, TRUE);
                if (sucesso != -1) {
                    resultado = receive_packet_tcp(socket_sistema_central);
                    limpar_data_resultado = TRUE;
                }

                if (resultado.socket == -1 || resultado.payload.code != REQUEST_CODE_ACK) {
                    desconectado = TRUE;

                    printf("Desconectado do sistema central - tentativa de reconexão em 3s\n");
                    if (resultado.payload.code == REQUEST_CODE_NACK) {
                        resultado.payload.data = realloc(resultado.payload.data, resultado.payload.data_length + 1);
                        resultado.payload.data[resultado.payload.data_length] = 0; // caso não tenha sido enviada com um zero no final
                        printf("Mensagem do sistema central: %s\n", resultado.payload.data);
                    }

                    sleep(3);

                    packet.socket = ligar_ao_servidor_central();
                } else {
                    if (desconectado) {
                        desconectado = FALSE;
                        printf("Reconectado ao sistema central\n\n");
                    }
                }

                if (limpar_data_resultado) {
                    free(resultado.payload.data);
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
