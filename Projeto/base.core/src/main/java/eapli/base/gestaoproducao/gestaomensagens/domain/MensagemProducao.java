package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.application.IdentificadorDeLote;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.PRODUCAO)
public class MensagemProducao extends Mensagem{
    //P1 -> Máquina;TipoMsg;DataHora;Produto;Quantidade;Lote
    //Produto e quantidade são parametros obrigatorios ,lote opcional
    public final Date dataHora;
    public  final CodigoUnico codigoUnico;
    private int quantidade;
    public final IdentificadorDeLote identificadorDeLote;


    protected MensagemProducao(){
        dataHora=null;
        codigoUnico=null;
        identificadorDeLote=null;
    }

    public MensagemProducao(CodigoInternoMaquina codigoInternoMaquina, Date dataHora, CodigoUnico codigoUnico, int quantidade, IdentificadorDeLote identificadorDeLote) {
        super(TipoDeMensagem.PRODUCAO,new TimestampEmissao(dataHora),codigoInternoMaquina);
        if (codigoUnico==null && dataHora==null && quantidade<=0)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.dataHora = dataHora;
        this.codigoUnico = codigoUnico;
        this.quantidade = quantidade;
        this.identificadorDeLote = identificadorDeLote;
    }

}
