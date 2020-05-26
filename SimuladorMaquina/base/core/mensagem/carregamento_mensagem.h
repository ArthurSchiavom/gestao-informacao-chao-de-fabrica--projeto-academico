#ifndef SIMULADORMAQUINA_CARREGAMENTO_MENSAGEM_H
#define SIMULADORMAQUINA_CARREGAMENTO_MENSAGEM_H

/**
 * Prepara o buffer de mensagens
 *
 * @param filepath caminho para o ficheiro
 * @return (1) TRUE em caso de sucesso ou (2) false em caso de erro
 */
char setup_leitor_mensagens(char *filepath);

char* proxima_mensagem();

char* mensagem_atual();

char fechar_ficheiro_mensagens();

#endif //SIMULADORMAQUINA_CARREGAMENTO_MENSAGEM_H
