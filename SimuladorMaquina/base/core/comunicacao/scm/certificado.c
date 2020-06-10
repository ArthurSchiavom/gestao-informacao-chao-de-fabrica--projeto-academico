#include "certificado.h"
#include <stdio.h>

#include <string.h>
#include <stdlib.h>

void boot_certificado(char *nome_fich_key_pem) {
    int len_dir = strlen(CAMINHO_DIRETORIO_CERTS);
    int len_extensao = 4;
    int len_nome = strlen(nome_fich_key_pem);
    int len_total = len_dir + len_extensao + len_nome + 1;

    caminho_fich_key = malloc(len_total);
    strcat(caminho_fich_key, CAMINHO_DIRETORIO_CERTS);
    strcat(caminho_fich_key, nome_fich_key_pem);
    strcat(caminho_fich_key, ".key");

    caminho_fich_pem = malloc(len_total);
    strcat(caminho_fich_pem, CAMINHO_DIRETORIO_CERTS);
    strcat(caminho_fich_pem, nome_fich_key_pem);
    strcat(caminho_fich_pem, ".pem");
}
