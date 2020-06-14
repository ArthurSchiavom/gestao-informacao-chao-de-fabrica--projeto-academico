package eapli.base.processamentoMensagens.compare;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;

import java.util.Comparator;

public class ComparatorMensagemPorIdCodigo implements Comparator<Mensagem> {

    @Override
    public int compare(Mensagem o1, Mensagem o2) {
        o1.identity().codigoInternoMaquina.codigoInterno.compareTo(o2.identity().codigoInternoMaquina.codigoInterno);
        return 0;
    }
}
