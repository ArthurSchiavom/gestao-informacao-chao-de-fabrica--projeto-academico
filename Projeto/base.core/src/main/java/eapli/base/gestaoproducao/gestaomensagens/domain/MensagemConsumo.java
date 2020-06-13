package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.CONSUMO)
public class MensagemConsumo extends Mensagem implements AggregateRoot<MensagemID> {
    @XmlElement(name = "deposito")
    public final CodigoDeposito codigo;
    @XmlElement(name = "produto")
    public final CodigoUnico codigoUnico;
    @XmlElement(name = "quantidadeDeMateriaPrima")
    private double quantidadeProduzir;

    //C0 -> Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito

    protected MensagemConsumo(){
        this.codigo=null;
        this.codigoUnico=null;
    }

    public MensagemConsumo(CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, double quantidadeProduzir,CodigoUnico codigoUnico) {
        super(TipoDeMensagem.CONSUMO,new TimestampEmissao(dataHora),codigoInternoMaquina);
        if ( codigoInternoMaquina ==null || dataHora==null || codigoUnico==null)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.codigo = codigo;
        this.quantidadeProduzir = quantidadeProduzir;
        this.codigoUnico=codigoUnico;
    }

    @XmlTransient
    public double getQuantidadeProduzir() {
        return quantidadeProduzir;
    }
}
