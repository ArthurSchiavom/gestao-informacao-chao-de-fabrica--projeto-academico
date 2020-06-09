#include "rececao_packets.h"
#include "../../utils/const.h"
#include "../comunicar.h"
#include "string.h"
#include "certificado.h"

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

void receber_packet(Ssl_socket_wrapper *wrapper) {
    SSL *ssl_con = wrapper->con;
    Payload resultado;
    int sucesso = receive_packet_tcp_on_open_ssl_con(ssl_con, &resultado);
    printf("Packet recebido\n");

    if (sucesso == FALSE) {
        printf("Falha ao tentar ler resposta recebida. - receção de packets\n");

        SSL_free(ssl_con);
        close(wrapper->socket);
        free(wrapper);
        return;
    }

    Packet_ssl resposta;
    resposta.connection = ssl_con;
    resposta.payload.version = CURRENT_PROTOCOL_VERSION;
    resposta.payload.id = id_maquina;
    resposta.payload.data_length = 0;

    if (resultado.id == id_maquina) {
        resposta.payload.code = REQUEST_CODE_ACK;
    } else {
        resposta.payload.code = REQUEST_CODE_NACK;
    }

    send_packet_tcp_on_open_ssl_con(resposta, TRUE);

    if (resposta.payload.code == REQUEST_CODE_ACK) {
        tratar_packet_tcp_recebido(&resultado);
    }

    SSL_free(ssl_con);
    close(wrapper->socket);
    free(wrapper);
    free(resultado.data);
}

void modulo_rececao_packets() {
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


    const SSL_METHOD *method;
    SSL_CTX *ctx;

    method = SSLv23_server_method();
    ctx = SSL_CTX_new(method);

    // Load server's certificate and key
    SSL_CTX_use_certificate_file(ctx, caminho_fich_pem, SSL_FILETYPE_PEM);
    SSL_CTX_use_PrivateKey_file(ctx, caminho_fich_key, SSL_FILETYPE_PEM);
    if (!SSL_CTX_check_private_key(ctx)) {
        puts("Erro SSL/TLS: Falha no carregamento do certificado/chave");
        close(sock);
        return;
    }

    // THE CLIENTS' CERTIFICATES ARE TRUSTED
    SSL_CTX_load_verify_locations(ctx, AUTH_CLIENTS_SSL_CERTS_FILE, NULL);

    // Restrict TLS version and cypher suite
    SSL_CTX_set_min_proto_version(ctx, TLS1_2_VERSION);
    SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");

    // The client must provide a certificate and it must be trusted, the handshake will fail otherwise
    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER | SSL_VERIFY_FAIL_IF_NO_PEER_CERT, NULL);

    adl = sizeof(from);
    for (;;) {
        int new_sock = accept(sock, (struct sockaddr *) &from, &adl);

        SSL *ssl_con = SSL_new(ctx);
        SSL_set_fd(ssl_con, new_sock);
        int suc = SSL_accept(ssl_con);
        if (suc != 1) {
            int error = SSL_get_error(ssl_con, suc);
            printf("Erro SSL/TLS: TLS erro no handshake - Code %d\n", error);

            printf("receção de packets tcp: handshake TLS recusado: cliente não autorizado\n");
            SSL_free(ssl_con);
            close(new_sock);
            continue;
        }
        X509 *cert = SSL_get_peer_certificate(ssl_con);
        X509_free(cert);

        Ssl_socket_wrapper *wrapper = malloc(sizeof(Ssl_socket_wrapper));
        wrapper->con = ssl_con;
        wrapper->socket = new_sock;

        pthread_t t;
        int success = pthread_create(&t, NULL, (void* (*)(void*)) receber_packet, wrapper);
        if (success != 0) {
            printf("Falha ao criar nova thread para ler um packet recebido.\n");
        }
    }
    close(sock);
}

int iniciar_rececao_packets(pthread_t *t) {
    int success = pthread_create(t, NULL, (void* (*)(void*)) modulo_rececao_packets, NULL);
    if (success != 0) {
        return FALSE;
    }

    return TRUE;
}
