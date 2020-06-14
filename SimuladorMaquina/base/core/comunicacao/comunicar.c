#include "comunicar.h"
#include "scm/certificado.h"
#include "scm/comunicacao_sistema_central.h"
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <netdb.h>
#include <string.h>
#include <pthread.h>

#define TIMEOUT_SECONDS 3
#define UDP_BUFFER_SIZE 512

unsigned short reverse_bytes_short(unsigned short num) {
    unsigned char *a;
    unsigned short resultado;

    a = (unsigned char *) (&(num));
    resultado = (*a) * 256;

    a = ((unsigned char *) (&(num))) + 1;
    resultado = resultado + (*a);

    return resultado;
}


int reverse_bytes_int(int num) {
    int swapped = ((num >> 24) & 0xff) | // move byte 3 to byte 0
                  ((num << 8) & 0xff0000) | // move byte 1 to byte 2
                  ((num >> 8) & 0xff00) | // move byte 2 to byte 1
                  ((num << 24) & 0xff000000); // byte 0 to byte 3
    return swapped;
}


Built_Payload build_payload(Payload payload, short inverterBytesDeNumeros) {
    Built_Payload resultado;
    int built_payload_size = PAYLOAD_STATIC_DATA_SIZE + payload.data_length;
    resultado.size = built_payload_size;

    resultado.content = malloc(built_payload_size);
    resultado.content[0] = (char) payload.version;
    resultado.content[1] = (char) payload.code;

    unsigned short id = payload.id;
    if (inverterBytesDeNumeros == TRUE) {
        id = reverse_bytes_short(id);
    }
    unsigned short *helper_short = (unsigned short *) &(resultado.content[2]);
    *helper_short = id;

    unsigned short data_length = payload.data_length;
    if (inverterBytesDeNumeros == TRUE) {
        data_length = reverse_bytes_short(data_length);
    }
    helper_short = (unsigned short *) &(resultado.content[4]);
    *helper_short = data_length;
    if (data_length > 0) {
        strcpy(resultado.content + PAYLOAD_STATIC_DATA_SIZE, payload.data);
    }

    return resultado;
}


struct timeval to;

int start_tcp_connection(char *target, char *porta) {
    int err, sock;
    struct addrinfo req, *list;

    to.tv_sec = TIMEOUT_SECONDS;
    to.tv_usec = 0;

    bzero((char *) &req, sizeof(req));
    // let getaddrinfo set the family depending on the supplied server address
    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;
    err = getaddrinfo(target, porta, &req, &list);
    if (err) {
        return -1;
    }
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1) {
        freeaddrinfo(list);
        return -1;
    }

    setsockopt(sock, SOL_SOCKET, SO_RCVTIMEO, (char *) &to, sizeof(to));
    setsockopt(sock, SOL_SOCKET, SO_SNDTIMEO, (char *) &to, sizeof(to));

    if (connect(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
        freeaddrinfo(list);
        close(sock);
        return -1;
    }

    return sock;
}




Packet_udp processamento_de_pedido_udp(unsigned char code) {
    Packet_udp reply;
    Payload *replySistemaCentral;
    int status;
    int tamanho = strlen(id_linha_producao) + 1;
    unsigned short *helper_short = &id_maquina;
    unsigned short *helper_int = &tamanho;
    switch (code) {
        case REQUEST_CODE_HELLO:
            replySistemaCentral = ler_ultimo_resultado_handshake_scm();
            reply.payload.data = malloc(strlen(id_linha_producao) + 1);
            reply.payload.version = CURRENT_PROTOCOL_VERSION;
            if (replySistemaCentral == NULL) {
                reply.payload.code = REQUEST_CODE_NACK;
            } else {
                reply.payload.code = replySistemaCentral->code;
            }
            reply.payload.id = *helper_short;
            strcpy(reply.payload.data, id_linha_producao);
            reply.payload.data_length = *helper_int;
            return reply;
        case REQUEST_CODE_RESET:
            status = handshake_sistema_central();
            if (status == TRUE) {
                replySistemaCentral = ler_ultimo_resultado_handshake_scm();
                if (replySistemaCentral == NULL) {
                    reply.payload.code = REQUEST_CODE_NACK;
                } else {
                    reply.payload.code = replySistemaCentral->code;
                }
            } else {
                printf("\nNao foi possivel a conexao com o sistema central,resposta sera NACK!\n");
                reply.payload.code = REQUEST_CODE_NACK;
            }
            reply.payload.data = malloc(strlen(id_linha_producao) + 1);
            reply.payload.version = replySistemaCentral->version;
            reply.payload.id = *helper_short;
            strcpy(reply.payload.data, id_linha_producao);
            reply.payload.data_length = *helper_int;
            return reply;
        default:
            printf("Codigo nao permitido!");
            break;
    }
}

_Noreturn void start_udp_server() {
    struct sockaddr_storage client;
    int err, sock;
    unsigned int adl;
    struct addrinfo req, *list;
    char cliIPtext[UDP_BUFFER_SIZE], cliPortText[UDP_BUFFER_SIZE];


    bzero((char *) &req, sizeof(req));
// request a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    req.ai_family = AF_INET6;
    req.ai_socktype = SOCK_DGRAM;
    req.ai_flags = AI_PASSIVE;

// Retorna um ou mais addrinfo structs
    err = getaddrinfo(NULL, PORTA_SMM, &req, &list);
    if (err) {
        printf("Failed to get local address, error: %s\n", gai_strerror(err));
        exit(1);
    }

//Cria um unbound socket e retorna um file descriptor
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1) {
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }

//Associa um endereco de socket a um socket identificado pelo descriptor que nao possui um endereco atribuido
    if (bind(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
        perror("Bind failed");
        close(sock);
        freeaddrinfo(list); //Liberta a memoria que foi alocada dinamincamente
        exit(1);
    }

    freeaddrinfo(list);

//Escreve uma string para o output
    adl = sizeof(client);
    Packet_udp resposta;

//Function shall receive a message from a connection-mode or connectionless-mode socket
//Returna o tamanho da mensagem em bytes,0 caso nao haja mensagens e -1 caso de erro.
//Receber pacote:
    while (1) {
        char *buffer = malloc(UDP_BUFFER_SIZE);
        recvfrom(sock, buffer, UDP_BUFFER_SIZE, 0, (struct sockaddr *) &client, &adl);
        resposta.payload.version = buffer[0];
        resposta.payload.code = buffer[1];
        unsigned short *helper_short = &(buffer[2]);
        resposta.payload.id = reverse_bytes_short(*helper_short);
        unsigned int *helper_int = &(buffer[4]);
        resposta.payload.data_length = reverse_bytes_int(*helper_int);
        resposta.payload.data = malloc(resposta.payload.data_length);
        strcpy(resposta.payload.data, buffer + 8);

        printf("Pedido recebido: %d, %d, %d, %d\n", resposta.payload.version, resposta.payload.code,
               resposta.payload.id, resposta.payload.data_length);

        Packet_udp reply = processamento_de_pedido_udp(resposta.payload.code);
        //Esta funcao faz a traducao do endereco do socket para um nome de um no e um servico de localizacao
        if (!getnameinfo((struct sockaddr *) &client, adl, cliIPtext, UDP_BUFFER_SIZE, cliPortText, UDP_BUFFER_SIZE,
                         NI_NUMERICHOST | NI_NUMERICSERV)) {
            printf("Request from node %s, port number %s\n", cliIPtext, cliPortText);
            Built_Payload built_payload = build_payload(reply.payload, FALSE);
            int value = sendto(sock, built_payload.content, built_payload.size, 0, (struct sockaddr *) &client,
                               adl);
            if (value < 0)
                printf("Falha ao enviar o reply");
        } else printf("Got request, but failed to get client address\n");
        free(buffer);

    }

}
int iniciar_servidor_udp(pthread_t *t) {
    int success = pthread_create(t, NULL, start_udp_server, NULL);
    if (success != 0) {
        return FALSE;
    }
    return TRUE;
}

int send_packet_tcp_on_open_socket(Packet_tcp packet, short inverterBytesDeNumeros) {
    Built_Payload payload = build_payload(packet.payload, inverterBytesDeNumeros);
    int success = write(packet.socket, payload.content, payload.size);

    if (success == -1) {
        return FALSE;
    }
    return TRUE;
}

int receive_packet_tcp_on_open_socket(int socket, Payload *resultado) {
    resultado->version = 0;
    resultado->code = 0;
    int sucesso = read(socket, &(resultado->version), 1);
    if (sucesso == -1)
        return FALSE;
    sucesso = read(socket, &(resultado->code), 1);
    if (sucesso == -1) {
        return FALSE;
    } else {
        ultimo_estado_pedido = resultado->code;
    }
    ultimo_estado_pedido = sucesso;
    sucesso = read(socket, &(resultado->id), 2);
    if (sucesso == -1)
        return FALSE;
    sucesso = read(socket, &(resultado->data_length), 2);
    if (sucesso == -1)
        return FALSE;
    resultado->id = reverse_bytes_short(resultado->id);
    resultado->data_length = reverse_bytes_short(resultado->data_length);

    if (resultado->data_length == 0) {
        resultado->data_length = 1;
        char *dummy = malloc(resultado->data_length);
        dummy[0] = 0;
        resultado->data = dummy;
    } else {
        resultado->data = malloc(resultado->data_length);
        sucesso = read(socket, resultado->data, resultado->data_length);
        if (sucesso == -1) {
            free(resultado->data);
            return FALSE;
        }
    }

    return TRUE;
}

/**
 * @return (1) TRUE em caso de sucesso ou (2) FALSE em caso de erro
 */
int handshake_tcp_socket_aberto(int socket, Payload *resultado, short inverterBytesDeNumeros) {
    Packet_tcp packet;
    packet.socket = socket;
    packet.payload.version = CURRENT_PROTOCOL_VERSION;
    packet.payload.code = REQUEST_CODE_HELLO;
    packet.payload.id = id_maquina;
    packet.payload.data_length = 0;

    int sucesso = send_packet_tcp_on_open_socket(packet, inverterBytesDeNumeros);
    if (sucesso == FALSE) {
        return FALSE;
    }

    sucesso = receive_packet_tcp_on_open_socket(socket, resultado);
    if (sucesso == FALSE) {
        return FALSE;
    }

    return TRUE;
}

int handshake_tcp(char *endereco, char *porta, Payload *resultado) {
    int sock = start_tcp_connection(endereco, porta);
    if (sock == -1) {
        return FALSE;
    }

    int sucesso = handshake_tcp_socket_aberto(sock, resultado, TRUE);

    close(sock);
    return sucesso;
}

int enviar_packet_tcp(char *target, char *porta, Payload payload, Payload *resultado, short inverterBytesDeNumeros) {
    int sock = start_tcp_connection(target, porta);
    if (sock == -1) {
        return FALSE;
    }

    Packet_tcp packet;
    packet.payload = payload;
    packet.socket = sock;

    int sucesso = send_packet_tcp_on_open_socket(packet, inverterBytesDeNumeros);

    if (sucesso == TRUE) {
        sucesso = receive_packet_tcp_on_open_socket(sock, resultado);
    }

    close(sock);
    return sucesso;
}


SSL *start_tls_tcp_connection(char *target, char *porta, int *sock) {
    *sock = start_tcp_connection(target, porta);
    if (*sock == -1) {
        return NULL;
    }

    const SSL_METHOD *method = SSLv23_client_method();
    SSL_CTX *ctx = SSL_CTX_new(method);

    // Load client's certificate and key
    SSL_CTX_use_certificate_file(ctx, caminho_fich_pem, SSL_FILETYPE_PEM);
    SSL_CTX_use_PrivateKey_file(ctx, caminho_fich_key, SSL_FILETYPE_PEM);
    if (!SSL_CTX_check_private_key(ctx)) {
        puts("Erro SSL/TLS: Falha no carregamento do certificado/chave\n");
        close(*sock);
        return NULL;
    }

    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER, NULL);

    // THE SERVER'S CERTIFICATE IS TRUSTED
    SSL_CTX_load_verify_locations(ctx, AUTH_CLIENTS_SSL_CERTS_FILE, NULL);

    // Restrict TLS version and cypher suites
    SSL_CTX_set_min_proto_version(ctx, TLS1_2_VERSION);
    SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");

    SSL *ssl_conn = SSL_new(ctx);
    SSL_set_fd(ssl_conn, *sock);
    int suc = SSL_connect(ssl_conn);
    if (suc != 1) {
        int error = SSL_get_error(ssl_conn, suc);
        printf("Erro SSL/TLS: TLS erro no handshake - Código %d\n", error);
        SSL_free(ssl_conn);
        close(*sock);
        return NULL;
    }

    if (SSL_get_verify_result(ssl_conn) != X509_V_OK) {
        printf("Erro SSL/TLS: Certificado do servidor inválido\n");
        SSL_free(ssl_conn);
        close(*sock);
        return NULL;
    }

    X509 *cert = SSL_get_peer_certificate(ssl_conn);
    X509_free(cert);

    if (cert == NULL) {
        printf("Erro SSL/TLS: nenhum certificado provido pelo servidor\n");
        SSL_free(ssl_conn);
        close(*sock);
        return NULL;
    }

    return ssl_conn;
}

int send_packet_tcp_on_open_ssl_con(Packet_ssl packet, short inverterBytesDeNumeros) {
    Built_Payload payload = build_payload(packet.payload, inverterBytesDeNumeros);
    int success = SSL_write(packet.connection, payload.content, payload.size);

    if (success <= 0) {
        return FALSE;
    }
    return TRUE;
}

int receive_packet_tcp_on_open_ssl_con(SSL *con, Payload *resultado) {
    resultado->version = 0;
    resultado->code = 0;

    int sucesso = SSL_read(con, &(resultado->version), 1);
    if (sucesso <= 0) {
        return FALSE;
    }
    sucesso = SSL_read(con, &(resultado->code), 1);
    if (sucesso <= 0) {
        return FALSE;
    }
    sucesso = SSL_read(con, &(resultado->id), 1);
    if (sucesso <= 0) {
        return FALSE;
    }
    resultado->id = resultado->id * 256;
    sucesso = SSL_read(con, &(resultado->id), 1);
    if (sucesso <= 0) {
        return FALSE;
    }
    sucesso = SSL_read(con, &(resultado->data_length), 1);
    if (sucesso <= 0) {
        return FALSE;
    }
    resultado->data_length = resultado->data_length * 256;
    sucesso = SSL_read(con, &(resultado->data_length), 1);
    if (sucesso <= 0) {
        return FALSE;
    }

    if (resultado->data_length == 0) {
        resultado->data_length = 1;
        char *dummy = malloc(resultado->data_length);
        dummy[0] = 0;
        resultado->data = dummy;
    } else {
        resultado->data = malloc(resultado->data_length + 1);
        resultado->data[resultado->data_length] = 0;
        sucesso = SSL_read(con, resultado->data, resultado->data_length);
        if (sucesso <= 0) {
            free(resultado->data);
            return FALSE;
        }
    }

    return TRUE;
}

/**
 * @return (1) TRUE em caso de sucesso ou (2) FALSE em caso de erro
 */
int handshake_ssl_con_aberta(SSL *con, Payload *resultado, short inverterBytesDeNumeros) {
    Packet_ssl packet;
    packet.connection = con;
    packet.payload.version = CURRENT_PROTOCOL_VERSION;
    packet.payload.code = REQUEST_CODE_HELLO;
    packet.payload.id = id_maquina;
    packet.payload.data_length = 0;

    int sucesso = send_packet_tcp_on_open_ssl_con(packet, inverterBytesDeNumeros);
    if (sucesso == FALSE) {
        return FALSE;
    }

    if (sucesso == TRUE) {
        sucesso = receive_packet_tcp_on_open_ssl_con(con, resultado);
    }

    return sucesso;
}

int handshake_ssl(char *target, char *porta, Payload *resultado, short inverterBytesDeNumeros) {
    int sock;
    SSL *ssl_con = start_tls_tcp_connection(target, porta, &sock);
    if (ssl_con == NULL) {
        return FALSE;
    }

    int sucesso = handshake_ssl_con_aberta(ssl_con, resultado, inverterBytesDeNumeros);
    if (sucesso == FALSE) {
        return FALSE;
    }

    SSL_free(ssl_con);
    close(sock);
    return TRUE;
}

int
enviar_packet_tcp_tls(char *target, char *porta, Payload payload, Payload *resultado, short inverterBytesDeNumeros) {
    int sock;
    SSL *ssl_con = start_tls_tcp_connection(target, porta, &sock);
    if (ssl_con == NULL) {
        return FALSE;
    }

    Packet_ssl packet;
    packet.payload = payload;
    packet.connection = ssl_con;

    int sucesso = send_packet_tcp_on_open_ssl_con(packet, inverterBytesDeNumeros);

    if (sucesso == TRUE) {
        sucesso = receive_packet_tcp_on_open_ssl_con(ssl_con, resultado);
    }

    SSL_free(ssl_con);
    close(sock);
    return sucesso;
}
