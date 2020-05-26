#include <stdlib.h>
#include "boot.h"
#include "../maquina/informacao_maquina.h"
#include "../comunicacao/informacao_comunicacao.h"
#include "../mensagem/carregamento_mensagem.h"
#include "../comunicacao/comunicar.h"
#include "../utils/const.h"
#include <string.h>

int boot(char *id_maquina_param, char *intervalo_segundos_param,
        char *caminho_ficheiro_mensagens_param, char *endereco_sistema_central_param,
          char *endereco_smm_param) {
    id_maquina = (short) atoi(id_maquina_param);
    intervalo_entre_mensagens_segundos = atoi(intervalo_segundos_param);
    setup_leitor_mensagens(caminho_ficheiro_mensagens_param);
    endereco_sistema_central = malloc(strlen(endereco_sistema_central_param) + 1);
    strcpy(endereco_sistema_central, endereco_sistema_central_param);
    endereco_smm = malloc(strlen(endereco_smm_param) + 1);
    strcpy(endereco_smm, endereco_smm_param);

    int sucesso = ligar_ao_servidor_central();

    if (sucesso == -1) {
        return -1;
    }

    return TRUE;
}
