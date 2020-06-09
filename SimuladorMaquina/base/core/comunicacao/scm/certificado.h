#ifndef SIMULADORMAQUINA_CERTIFICADO_H
#define SIMULADORMAQUINA_CERTIFICADO_H

#define CAMINHO_DIRETORIO_CERTS "Certificados/"
#define AUTH_CLIENTS_SSL_CERTS_FILE "Certificados/authentic-clients.pem"

char *caminho_fich_key;
char *caminho_fich_pem;

void boot_certificado(char *nome_fich_key_pem);

#endif //SIMULADORMAQUINA_CERTIFICADO_H
