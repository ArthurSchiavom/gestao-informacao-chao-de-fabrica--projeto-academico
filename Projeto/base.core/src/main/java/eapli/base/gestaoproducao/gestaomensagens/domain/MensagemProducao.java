package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.IdentificadorDeLote;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.PRODUCAO)
public class MensagemProducao extends Mensagem implements AggregateRoot<MensagemID> {
    //P1 -> Máquina;TipoMsg;DataHora;Produto;Quantidade;Lote
    //Produto e quantidade são parametros obrigatorios ,lote opcional
    @XmlElement(name = "produto")
    public  final CodigoUnico codigoUnico;

    @XmlElement(name = "quantidade")
    private double quantidade;

    @XmlElement(name = "lote")
    public final IdentificadorDeLote identificadorDeLote;

    protected MensagemProducao(){
        codigoUnico=null;
        identificadorDeLote=null;
    }

    public MensagemProducao(CodigoInternoMaquina codigoInternoMaquina, Date dataHora, CodigoUnico codigoUnico, double quantidade, IdentificadorDeLote identificadorDeLote) {
        super(TipoDeMensagem.PRODUCAO,new TimestampEmissao(dataHora),codigoInternoMaquina);
        if (codigoUnico==null || dataHora==null || quantidade<=0)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.codigoUnico = codigoUnico;
        this.quantidade = quantidade;
        this.identificadorDeLote = identificadorDeLote;
    }

    @XmlTransient
    public double getQuantidade() {
        return quantidade;
    }
}
