#include "envio_mensagens_carregadas.h"

void tentar_enviar_mensagem(Payload payload) {
    short limpar_data_resultado;
    int sucesso;

    Payload resultado;
    resultado.code = REQUEST_CODE__INTERNO_SEM_SIGNIFICADO;
    resultado.version = -1;
    while (resultado.code != REQUEST_CODE_ACK) {
        limpar_data_resultado = TRUE;
        sucesso = enviar_packet_tcp_tls(endereco_sistema_central, PORTA_SISTEMA_CENTRAL, payload, &resultado, TRUE);
        if (sucesso == FALSE || resultado.code != REQUEST_CODE_ACK) {
            //TODO - REMOVE
            printf("RECONNECT REASON - suc: %d, code: %hu\n", sucesso, resultado.code);
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
    sleep_ate_primeira_conexao_ser_bem_sucedida();

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
    int success = pthread_create(t, NULL, (void* (*)(void*)) modulo_envio_mensagens, NULL);
    if (success != 0) {
        return FALSE;
    }

    return TRUE;
}
