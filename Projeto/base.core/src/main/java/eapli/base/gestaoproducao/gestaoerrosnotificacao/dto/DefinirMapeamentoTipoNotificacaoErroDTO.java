package eapli.base.gestaoproducao.gestaoerrosnotificacao.dto;

import eapli.base.infrastructure.application.DefinirMapeamentoAction;
import eapli.base.infrastructure.application.EntryImpl;

import java.util.Map;

public class DefinirMapeamentoTipoNotificacaoErroDTO implements DefinirMapeamentoAction<String, TipoNotificacaoErroDTO, TipoNotificacaoErroDTO> {
    @Override
    public Map.Entry gerarEntrada(TipoNotificacaoErroDTO t) {
        return new EntryImpl<String, TipoNotificacaoErroDTO>(t.nomeDisplay, t);
    }
}
