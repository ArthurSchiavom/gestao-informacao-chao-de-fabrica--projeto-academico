package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.CONSUMO)
public class MensagemConsumo extends Mensagem implements AggregateRoot<MensagemID> {
    @XmlElement(name = "materiaPrima")
    public final String idMateriaPrima;
    @XmlElement(name = "deposito")
    public final CodigoDeposito codigoDeposito;
    @XmlElement(name = "produto")
    private double quantidadeProduzir;

    //C0 -> Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito

    protected MensagemConsumo(){
        this.codigoDeposito =null;
        this.idMateriaPrima=null;
    }

    public MensagemConsumo(CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora,
                           double quantidadeProduzir,String idMateriaPrima) {
        super(TipoDeMensagem.CONSUMO,new TimestampEmissao(dataHora),codigoInternoMaquina);
        if (idMateriaPrima.isEmpty() || dataHora == null)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.codigoDeposito = codigo;
        this.quantidadeProduzir = quantidadeProduzir;
        this.idMateriaPrima=idMateriaPrima;
    }

    @XmlTransient
    public double getQuantidadeProduzir() {
        return quantidadeProduzir;
    }

    @XmlTransient
    public String getIdMateriaPrima() {
        return idMateriaPrima;
    }
}
