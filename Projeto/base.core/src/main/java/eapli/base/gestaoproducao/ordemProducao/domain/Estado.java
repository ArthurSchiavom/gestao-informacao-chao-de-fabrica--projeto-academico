package eapli.base.gestaoproducao.ordemProducao.domain;

/**
 *Estados de ordens de produçáo
 *
 * Enunciado:
 *
 *      " Por omissão, uma ordem de produção assume o estado de “pendente”
 *
 *      "Uma ordem de produção posse assumir outros estados:
 *      i. “Em Execução” ou “Execução Parada Temporariamente” ou “Concluída”. Estes
 *      estados são atribuídos automaticamente com base em informação de mensagens
 *      enviadas pelas máquinas;
 *      ii. “Suspensa”. Atribuído manualmente por um utilizador"
 */
public enum Estado {
    PENDENTE, SUSPENSA, EM_EXECUCAO, EXECUCAO_PARADA_TEMPORARIAMENTE, CONCLUIDA
}
