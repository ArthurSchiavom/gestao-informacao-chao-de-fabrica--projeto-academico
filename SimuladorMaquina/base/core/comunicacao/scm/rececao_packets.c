#include "rececao_packets.h"
#include "../../utils/const.h"
#include "../comunicar.h"

#define BUF_SIZE 300
#define SUFIXO_FICHEIRO ".config"
#define PATH_PASTA_OUTPUT_CONFIG "ficheiros_config/"

char *proximo_caminho_ficheiro_config() {
    criar_diretorio(PATH_PASTA_OUTPUT_CONFIG);

    char **caminhos;
    int actual_tamanho; // para gestão automatica da memória
    int *n_ficheiros = &actual_tamanho;
    int i, j;
    caminhos = nomes_ficheiros(PATH_PASTA_OUTPUT_CONFIG, n_ficheiros);

    int maior = -1;
    int possivel_maior;
    char temp;
    for (i = 0; i < *n_ficheiros; i++) {
        j = 0;
        temp = caminhos[i][0];
        while (temp != '.') {
            j++;
            temp = caminhos[i][j];
        }

        caminhos[i][j] = 0;
        possivel_maior = atoi(caminhos[i]);
        if (possivel_maior > maior) {
            maior = possivel_maior;
        }
    }

    int numero_novo_ficheiro = maior + 1;
    char *prefixo_novo_ficheiro;
    prefixo_novo_ficheiro = malloc(10);
    sprintf(prefixo_novo_ficheiro, "%d", numero_novo_ficheiro);

    int length_path = strlen(PATH_PASTA_OUTPUT_CONFIG);
    int length_prefixo = strlen(prefixo_novo_ficheiro);
    int length_sufixo = strlen(SUFIXO_FICHEIRO);
    int length_total = length_path + length_prefixo + length_sufixo + 1;

    char *novo_nome = malloc(length_total);
    sprintf(novo_nome, "%s%s%s", PATH_PASTA_OUTPUT_CONFIG, prefixo_novo_ficheiro, SUFIXO_FICHEIRO);

    for (i = 0; i < *n_ficheiros; i++) {
        free(caminhos[i]);
    }
    if (*n_ficheiros > 0) {
        free(caminhos);
    }

    free(prefixo_novo_ficheiro);

    return novo_nome;
}

void tratar_packet_tcp_recebido(Payload *payload_recebido) {
    payload_recebido->data = realloc(payload_recebido->data, payload_recebido->data_length + 1);
    payload_recebido->data[payload_recebido->data_length] = 0; // Caso não tenha sido enviado um 0 no final

    if (payload_recebido->code == REQUEST_CODE_CONFIG) {
        char *caminho_ficheiro = proximo_caminho_ficheiro_config();

        FILE *fp;
        fp = fopen(caminho_ficheiro, "w+");
        fputs(payload_recebido->data, fp);
        fclose(fp);

        free(caminho_ficheiro);
    }
}

void receber_packet(int *socket) {
    Payload resultado;
    int sucesso = receive_packet_tcp_on_open_socket(*socket, &resultado);
    if (sucesso == FALSE) {
        printf("Falha ao tentar ler resposta recebido. - receção de packets\n");
    }

    Packet_tcp resposta;
    resposta.socket = *socket;
    resposta.payload.version = CURRENT_PROTOCOL_VERSION;
    resposta.payload.id = id_maquina;
    resposta.payload.data_length = 0;

    if (resultado.id == id_maquina) {
        resposta.payload.code = REQUEST_CODE_ACK;
    }
    else {
        resposta.payload.code = REQUEST_CODE_NACK;
    }

    send_packet_tcp_on_open_socket(resposta, TRUE);

    if (resposta.payload.code == REQUEST_CODE_ACK) {
        tratar_packet_tcp_recebido(&resultado);
    }

    close(*socket);
    free(socket);
}

_Noreturn void modulo_rececao_packets() {
    struct sockaddr_storage from;
    int err, sock;
    unsigned int adl;
    struct addrinfo req, *list;
    bzero((char *) &req, sizeof(req));

    // requesting a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    req.ai_family = AF_INET6;
    req.ai_socktype = SOCK_STREAM;
    req.ai_flags = AI_PASSIVE; // local address
    err = getaddrinfo(NULL, PORTA_RECECAO_MAQUINA, &req, &list);
    if (err) {
        printf("Falha ao tentar obter endereço local - receção de packets, error: %s\n", gai_strerror(err));
        return;
    }

    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1) {
        perror("Falha ao tentar abrir socket - receção de packets");
        freeaddrinfo(list);
        return;
    }

    if (bind(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
        perror("Bind falhou - receção de packets");
        close(sock);
        freeaddrinfo(list);
        return;
    }
    freeaddrinfo(list);

    listen(sock, SOMAXCONN);
    adl = sizeof(from);
    for (;;) {
        int newSock = accept(sock, (struct sockaddr *) &from, &adl);

        pthread_t t;
        int *socket_pointer = malloc(sizeof(int));
        *socket_pointer = newSock;
        int success = pthread_create(&t, NULL, receber_packet, socket_pointer);
        if (success != 0) {
            printf("Falha ao criar nova thread para receber packet.");
        }
    }
    close(sock);
}

int iniciar_rececao_packets(pthread_t *t) {
    int success = pthread_create(t, NULL, modulo_rececao_packets, NULL);
    if (success != 0) {
        return FALSE;
    }

    return TRUE;
}
