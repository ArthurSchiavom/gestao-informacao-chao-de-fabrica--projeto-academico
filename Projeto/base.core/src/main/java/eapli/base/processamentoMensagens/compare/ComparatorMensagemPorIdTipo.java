package eapli.base.processamentoMensagens.compare;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;

import java.util.Comparator;

public class ComparatorMensagemPorIdTipo implements Comparator<Mensagem> {

    @Override
    public int compare(Mensagem o1, Mensagem o2) {
        o1.identity().tipoDeMensagem.compareTo(o2.identity().tipoDeMensagem);
        return 0;
    }
}
