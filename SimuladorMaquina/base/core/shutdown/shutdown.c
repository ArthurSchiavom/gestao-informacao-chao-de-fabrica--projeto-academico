#include <malloc.h>
#include "shutdown.h"
#include "../comunicacao/informacao_comunicacao.h"
#include "../mensagem/carregamento_mensagem.h"
#include "../comunicacao/comunicar.h"

int shutdown() {
    close_tcp_connection(socket_sistema_central);
    free(endereco_sistema_central);
    fechar_ficheiro_mensagens();
    return 0;
}
