#include <stdio.h>
#include "gestor_mensagem.h"
#include "../utils/const.h"
#include <string.h>

char line[300];
FILE *file;

/**
 *
 * @return (1) next line if such or (2) empty line if no next line
 */
char *proxima_mensagem() {
    line[1] = 0;
    while (line[1] == 0 || line[0] == 0) {
        if (!fgets(line, sizeof(line), file)) {
            line[0] = 0;
            return line;
        }
    }
    strtok(line, "\n");
    return line;
}

char setup_leitor_mensagens(char *filepath) {
    file = fopen(filepath, "r"); /* should check the result */
    line[0] = 0;
    line[1] = 0;
    return TRUE;
}

/**
 *
 * @return (1) current line if such exists or (2) empty line if none
 */
char *linha_atual() {
    return line;
}

char close() {
    if (fclose(file) == 0)
        return TRUE;
    else
        return FALSE;
}
