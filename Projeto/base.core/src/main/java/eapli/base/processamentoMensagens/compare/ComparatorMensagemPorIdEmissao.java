package eapli.base.processamentoMensagens.compare;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;

import java.util.Comparator;

public class ComparatorMensagemPorIdEmissao implements Comparator<Mensagem> {

    @Override
    public int compare(Mensagem o1, Mensagem o2) {
        o1.identity().tempoEmissao.timestamp.compareTo(o2.identity().tempoEmissao.timestamp);
        return 0;
    }
}
