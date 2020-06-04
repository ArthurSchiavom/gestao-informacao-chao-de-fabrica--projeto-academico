#include <sys/dirent.h>
#include <dirent.h>
#include <errno.h>
#include "ficheiros.h"
#include "const.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

char** nomes_ficheiros(char *caminho_diretorio, int *n_ficheiros) {
    DIR* dir;
    struct dirent *ent;
    *n_ficheiros = 0;
    char** resultado;
    if ((dir = opendir(caminho_diretorio)) != NULL) {
        while ((ent = readdir(dir)) != NULL) {
            if (strcmp(ent->d_name, ".") == 0 || strcmp(ent->d_name, "..") == 0) {
                continue;
            }

            (*n_ficheiros)++;
            if (*n_ficheiros == 1) {
                resultado = (char **) malloc(sizeof(char *));
            } else {
                resultado = (char **) realloc(resultado, sizeof(char *) * (*n_ficheiros));
            }

            resultado[*n_ficheiros - 1] = malloc(strlen(ent->d_name) + 1);
            strcpy(resultado[*n_ficheiros - 1], ent->d_name);
        }

        closedir(dir);
    } else {
        return NULL;
    }

    return resultado;
}
