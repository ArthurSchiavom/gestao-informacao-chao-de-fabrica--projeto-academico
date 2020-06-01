#ifndef SIMULADORMAQUINA_INFORMACAO_COMUNICACAO_H
#define SIMULADORMAQUINA_INFORMACAO_COMUNICACAO_H

#define CURRENT_PROTOCOL_VERSION 0
#define PORTA_SISTEMA_CENTRAL "6834"
#define PORTA_SMM "7194"

#define REQUEST_CODE_HELLO 0
#define REQUEST_CODE_MSG 1
#define REQUEST_CODE_CONFIG 2
#define REQUEST_CODE_RESET 3
#define REQUEST_CODE__INTERNO_SEM_SIGNIFICADO 55
#define REQUEST_CODE_ACK 150
#define REQUEST_CODE_NACK 151

#define PAYLOAD_STATIC_DATA_SIZE 6

int intervalo_entre_mensagens_segundos;
char *endereco_sistema_central;
char *endereco_smm;

typedef struct Payload {
    unsigned char version;
    unsigned char code;
    unsigned short id;
    unsigned short data_length;
    char *data;
} Payload;

#endif //SIMULADORMAQUINA_INFORMACAO_COMUNICACAO_H
