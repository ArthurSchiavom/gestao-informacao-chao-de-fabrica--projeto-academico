package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;

import eapli.framework.representations.dto.DTOable;

/**
 * Enum para o tipo de erro.
 * DADOS_INVALIDOS - para quando os dados introduzidos estão inválidos(e.g. alfanumérico em campo inteiro)
 * ELEMENTOS_INEXISTENTES - Referência a elementos não especificados no sistema (e.g. ordens de produção
 * não carregadas no sistema; matéria-prima não definida)
 */
public enum TipoErroNotificacao {
	DADOS_INVALIDOS, ELEMENTOS_INEXISTENTES;
}
