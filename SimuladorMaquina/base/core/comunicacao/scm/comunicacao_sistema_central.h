#ifndef SIMULADORMAQUINA_COMUNICACAO_SISTEMA_CENTRAL_H
#define SIMULADORMAQUINA_COMUNICACAO_SISTEMA_CENTRAL_H

#include "../../utils/const.h"

#include <unistd.h>
#include <malloc.h>
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include "../informacao_comunicacao.h"
#include "../comunicar.h"

static int primeira_conexao_ao_sistema_central_terminada = FALSE;
static int a_reconectar_ao_sistema_central = FALSE;
static pthread_mutex_t mutex_a_reconectar_ao_sistema_central;

static Payload ultimo_resultado_handshake_scm;
static int sucesso_ultimo_resultado_handshake_scm = FALSE;
static pthread_mutex_t mutex_ultimo_resultado_handshake_scm;

void sleep_ate_primeira_conexao_ser_bem_sucedida();

void reconectar_sistema_central();

/**
 * @return (1) FALSE em caso de não conseguir contactar o servidor ou (2) TRUE caso contrário.
 * No primeiro caso o payload não terá informação válida. No segundo caso o payload estará preenchido com a resposta do servidor
 * e o campo data deve ser sempre manualmente libertado da memória.
 */
int handshake_sistema_central();

/**
 * @return (1) FALSE em caso de não conseguir contactar o servidor ou (2) TRUE caso contrário.
 */
int handshake_sistema_central_ate_sucesso();

Payload *ler_ultimo_resultado_handshake_scm();

#endif //SIMULADORMAQUINA_COMUNICACAO_SISTEMA_CENTRAL_H
