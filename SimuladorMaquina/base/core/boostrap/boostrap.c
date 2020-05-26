#include <stdlib.h>
#include "boostrap.h"
#include "../maquina/informacao_maquina.h"
#include "../comunicacao/informacao_comunicacao.h"
#include "../mensagem/gestor_mensagem.h"
#include "../comunicacao/comunicar.h"

char boot(char *id_maquina_param, char *intervalo_segundos_param, char *caminho_ficheiro_mensagens_param, char *endereco_sistema_central_param) {
    id_maquina = id_maquina_param;
    intervalo_entre_mensagens_segundos = atoi(intervalo_segundos_param);
    setup_leitor_mensagens(caminho_ficheiro_mensagens_param);
    endereco_sistema_central = endereco_sistema_central_param;
}
