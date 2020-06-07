#ifndef SIMULADORMAQUINA_FICHEIROS_H
#define SIMULADORMAQUINA_FICHEIROS_H

/**
 * @return Os nomes em caso de sucesso e NULL caso contrário. A matriz deve ser manualmente libertada.
 */
char** nomes_ficheiros(char *caminho_diretorio, int *n_ficheiros);

/**
 * Não faz nada se o diretório já existir
 */
void criar_diretorio();

#endif //SIMULADORMAQUINA_FICHEIROS_H
