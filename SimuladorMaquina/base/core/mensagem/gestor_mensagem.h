#ifndef SIMULADORMAQUINA_GESTOR_MENSAGEM_H
#define SIMULADORMAQUINA_GESTOR_MENSAGEM_H

/**
 * Prepara o buffer de mensagens
 *
 * @param filepath caminho para o ficheiro
 * @return (1) TRUE em caso de sucesso ou (2) false em caso de erro
 */
char setup_leitor_mensagens(char *filepath);

char* proxima_mensagem();

char* linha_atual();

#endif //SIMULADORMAQUINA_GESTOR_MENSAGEM_H
