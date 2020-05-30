package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.application.IdentificadorDeLote;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;

import java.util.Date;

public class MensagemProducao extends Mensagem{
    //P1 -> Máquina;TipoMsg;DataHora;Produto;Quantidade;Lote
    //Produto e quantidade são parametros obrigatorios ,lote opcional
    public final CodigoInternoMaquina codigoInternoMaquina;
    public final Date dataHora;
    public  final CodigoUnico codigoUnico;
    private  int quantidade;
    public final IdentificadorDeLote identificadorDeLote;


    protected MensagemProducao(){
        codigoInternoMaquina=null;
        dataHora=null;
        codigoUnico=null;
        identificadorDeLote=null;
    }

    public MensagemProducao(CodigoInternoMaquina codigoInternoMaquina, Date dataHora, CodigoUnico codigoUnico, int quantidade, IdentificadorDeLote identificadorDeLote) {
        super(TipoDeMensagem.PRODUCAO,new TimestampEmissao(dataHora));
        if (codigoInternoMaquina==null && dataHora==null && codigoUnico==null && quantidade<0)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.codigoInternoMaquina = codigoInternoMaquina;
        this.dataHora = dataHora;
        this.codigoUnico = codigoUnico;
        this.quantidade = quantidade;
        this.identificadorDeLote = identificadorDeLote;
    }
}
