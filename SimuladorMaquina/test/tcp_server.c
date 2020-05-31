#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

#define BUF_SIZE 300
#define SERVER_PORT "6834"

#define REQUEST_CODE_HELLO 0
#define REQUEST_CODE_MSG 1
#define REQUEST_CODE_CONFIG 2
#define REQUEST_CODE_RESET 3
#define REQUEST_CODE_ACK 150
#define REQUEST_CODE_NACK 151

typedef struct Payloadd {
    unsigned char version;
    unsigned char code;
    unsigned short id;
    unsigned short data_length;
    char *data;
} Payloadd;

typedef struct Packett {
    Payloadd payload;
    int socket;
} Packett;

typedef struct Built_Payloadd {
    int size;
    char *content;
} Built_Payloadd;

short reverse_bytes_short(short num) {
    short swapped = (num >> 8) | (num << 8);
    return swapped;
}

Built_Payloadd build_payload(Payloadd payload) {
    Built_Payloadd resultado;
    int built_payload_size = 6 + payload.data_length;
    resultado.size = built_payload_size;

    resultado.content = malloc(built_payload_size);
    resultado.content[0] = payload.version;
    resultado.content[1] = payload.code;

    unsigned short id = payload.id;
    unsigned short *helper_short = &(resultado.content[2]);
    *helper_short = id;

    unsigned short data_length = payload.data_length;
    helper_short = &(resultado.content[4]);
    *helper_short = data_length;
    strcpy(resultado.content + 6, payload.data);


    return resultado;
}

int start_tcp_connection(char *target, char *porta) {
    int err, sock;
    struct addrinfo req, *list;

    bzero((char *) &req, sizeof(req));
    // let getaddrinfo set the family depending on the supplied server address
    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;
    err = getaddrinfo(target, porta, &req, &list);
    if (err) {
        printf("Falha ao tentar obter endereço IP: %s\n", gai_strerror(err));
        return -1;
    }
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1) {
        perror("Falha ao tentar abrir socket.");
        freeaddrinfo(list);
        return -1;
    }
    if (connect(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
        perror("Falha ao tentar conectar.");
        freeaddrinfo(list);
        close(sock);
        return -1;
    }
    return sock;
}

int send_packet_tcp(Packett packet) {
    Built_Payloadd payload = build_payload(packet.payload);
    write(packet.socket, payload.content, payload.size);

    free(payload.content);

    return 1;
}

void close_tcp_connectionn(int socket) {
    close(socket);
}

Packett receive_packet_tcpp(int socket) {
    Packett result;

    result.socket = socket;
    read(socket, &(result.payload.version), 1);
    read(socket, &(result.payload.code), 1);
    read(socket, &(result.payload.id), 2);
    read(socket, &(result.payload.data_length), 2);

    result.payload.id = result.payload.id;
    result.payload.data_length = result.payload.id;

    int success;
    if (result.payload.data_length == 0) {
        result.payload.data_length = 1;
        char *dummy = malloc(1);
        dummy[0] = 0;
        result.payload.data = dummy;
        success = 1;
    }
    else {
        result.payload.data = malloc(result.payload.data_length);
        success = read(socket, result.payload.data, result.payload.data_length);
    }

    if (success == -1) {
        result.socket = -1;
    }

    printf("READ FROM SOCKET: id: %d; length: %d\n", result.payload.id, result.payload.data_length);

    return result;
}

int handshake(int socket) {
    Packett packet;
    packet.socket = socket;
    packet.payload.version = 0;
    packet.payload.code = REQUEST_CODE_HELLO;
    packet.payload.id = 123;
    packet.payload.data_length = 1;
    packet.payload.data = malloc(1);
    packet.payload.data[0] = 0;

    printf("enviando packet hello\n");
    send_packet_tcp(packet);
    printf("enviado, à espera de resposta\n");
    Packett reply = receive_packet_tcpp(socket);

    return reply.payload.code;
}


int main(void) {
    struct sockaddr_storage from;
    int err, newSock, sock;
    unsigned int adl;
    unsigned long i, f, n, num, sum;
    unsigned char bt;
    char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
    struct addrinfo req, *list;
    bzero((char *) &req, sizeof(req));
    // requesting a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    req.ai_family = AF_INET6;
    req.ai_socktype = SOCK_STREAM;
    req.ai_flags = AI_PASSIVE; // local address
    err = getaddrinfo(NULL, SERVER_PORT, &req, &list);
    if (err) {
        printf("Failed to get local address, error: %s\n", gai_strerror(err));
        exit(1);
    }
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1) {
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }
    if (bind(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen) == -1) {
        perror("Bind failed");
        close(sock);
        freeaddrinfo(list);
        exit(1);
    }
    freeaddrinfo(list);
    listen(sock, SOMAXCONN);
    puts("Accepting TCP connections (IPv6/IPv4). Use CTRL+C to terminate the server");
    adl = sizeof(from);
    for (;;) {
        newSock = accept(sock, (struct sockaddr *) &from, &adl);
        if (!fork()) {
            int count = 0;
            close(sock);
            getnameinfo((struct sockaddr *) &from, adl, cliIPtext, BUF_SIZE,
                        cliPortText, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV);
            printf("New connection from %s, port number %s\n", cliIPtext, cliPortText);


            while (1) {
                Payloadd data;
//                Payloadd data = receive_packet_tcpp(newSock).payload;

                data.version = 0;
                data.code = 0;
                read(newSock, &(data.version), 1);
                read(newSock, &(data.code), 1);
                read(newSock, &(data.id), 2);
                read(newSock, &(data.data_length), 2);
                data.id = reverse_bytes_short(data.id);
                data.data_length = reverse_bytes_short(data.data_length);
                if (data.data_length > 0) {
                    data.data = malloc(data.data_length);
                    read(newSock, data.data, data.data_length);
                }
                else {
                    data.data = malloc(1);
                    data.data[0] = 0;
                }

                printf("\nPacket received! %d, %d, %d, %d, %s!\n", data.version, data.code, data.id, data.data_length,
                       data.data);

                if (data.code == 155)
                    break;

                free(data.data);

                Packett packet;
                packet.socket = newSock;
                packet.payload.version = 0;
                packet.payload.code = 150;
                packet.payload.id = 123;
                packet.payload.data_length = 3;
                packet.payload.data = malloc(3);
                packet.payload.data[0] = 'Y';
                packet.payload.data[1] = 'O';
                packet.payload.data[2] = 0;

                // test NACK
//                if (count == 4) {
//                    free(packet.payload.data);
//                    packet.payload.data_length = 3;
//                    packet.payload.data = malloc(3);
//                    packet.payload.data[0] = 'N';
//                    packet.payload.data[1] = 'O';
//                    packet.payload.data[2] = 0;
//                    packet.payload.code = 151;
//                }
//                count++;

                send_packet_tcp(packet);


                printf("Reply sent!\n");

                free(packet.payload.data);

            }
            printf("Connection %s, port number %s closed\n", cliIPtext, cliPortText);
            close(newSock);
            exit(0);
        }
    }
    close(sock);
}
