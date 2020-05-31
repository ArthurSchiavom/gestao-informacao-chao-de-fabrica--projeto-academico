package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.TipoNotificacaoErroDTO;
import eapli.base.infrastructure.application.ConvertableToDTO;

/**
 * Enum para o tipo de erro.
 * DADOS_INVALIDOS - para quando os dados introduzidos estão inválidos(e.g. alfanumérico em campo inteiro)
 * ELEMENTOS_INEXISTENTES - Referência a elementos não especificados no sistema (e.g. ordens de produção
 * não carregadas no sistema; matéria-prima não definida)
 */
public enum TipoErroNotificacao implements ConvertableToDTO<TipoNotificacaoErroDTO> {
	DADOS_INVALIDOS("Dados inválidos"), ELEMENTOS_INEXISTENTES("Elementos inexistentes");

	public final String nomeDisplay;

	TipoErroNotificacao(String nomeDisplay) {
		this.nomeDisplay = nomeDisplay;
	}

	@Override
	public TipoNotificacaoErroDTO toDTO() {
		return new TipoNotificacaoErroDTO(nomeDisplay);
	}

	public static TipoErroNotificacao actualValueOf(String nome) {
		for (TipoErroNotificacao tipo : values()) {
			if (tipo.nomeDisplay.equalsIgnoreCase(nome))
				return tipo;
		}

		return null;
	}
}
