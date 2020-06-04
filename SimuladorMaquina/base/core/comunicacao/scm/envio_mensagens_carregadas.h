#ifndef SIMULADORMAQUINA_ENVIO_MENSAGENS_CARREGADAS_H
#define SIMULADORMAQUINA_ENVIO_MENSAGENS_CARREGADAS_H

#include <pthread.h>
#include <string.h>

#include "../informacao_comunicacao.h"
#include "../../utils/const.h"
#include "../comunicar.h"
#include "comunicacao_sistema_central.h"
#include "../../maquina/informacao_maquina.h"
#include "../../mensagem/carregamento_mensagem.h"

int iniciar_envio_mensagens(pthread_t *t);

#endif //SIMULADORMAQUINA_ENVIO_MENSAGENS_CARREGADAS_H
