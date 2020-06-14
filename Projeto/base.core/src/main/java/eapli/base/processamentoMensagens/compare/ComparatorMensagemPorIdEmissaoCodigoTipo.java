package eapli.base.processamentoMensagens.compare;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;

import java.util.Comparator;

public class ComparatorMensagemPorIdEmissaoCodigoTipo implements Comparator<Mensagem> {

    @Override
    public int compare(Mensagem o1, Mensagem o2) {
        return new ComparatorMensagemPorIdEmissao()
                .thenComparing(new ComparatorMensagemPorIdCodigo())
                .thenComparing(new ComparatorMensagemPorIdTipo())
                .compare(o1, o2);
    }
}
