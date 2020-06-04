#ifndef SIMULADORMAQUINA_RECECAO_PACKETS_H
#define SIMULADORMAQUINA_RECECAO_PACKETS_H

#include <pthread.h>
#include <unistd.h>
#include "../informacao_comunicacao.h"
#include "../../utils/ficheiros.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>


int iniciar_rececao_packets(pthread_t *t);

#endif //SIMULADORMAQUINA_RECECAO_PACKETS_H
