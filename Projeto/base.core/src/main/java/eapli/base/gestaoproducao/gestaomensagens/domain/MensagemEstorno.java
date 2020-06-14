package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@DiscriminatorValue(value = TipoDeMensagem.Values.ESTORNO)
public class MensagemEstorno extends Mensagem implements AggregateRoot<MensagemID> {
    @XmlElement(name = "materiaPrima")
    public final String idMateriaPrima;

    @XmlElement(name = "deposito")
    public final CodigoDeposito codigoDeposito;

    @XmlElement(name = "quantidadeDeMateriaPrima")
    private double quantidadeProduzir;

    protected MensagemEstorno() {
        this.codigoDeposito = null;
        this.idMateriaPrima = null;
    }

    public MensagemEstorno(CodigoDeposito codigoDeposito, String idMateriaPrima, CodigoInternoMaquina codigoInternoMaquina,
                           Date dataHora, double quantidadeProduzir) {
        super(TipoDeMensagem.ESTORNO, new TimestampEmissao(dataHora), codigoInternoMaquina);
        if (idMateriaPrima == null || idMateriaPrima.isEmpty() || dataHora == null)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.idMateriaPrima = idMateriaPrima;
        this.codigoDeposito = codigoDeposito;
        this.quantidadeProduzir = quantidadeProduzir;
    }

    @XmlTransient
    public String getIdMateriaPrima() {
        return idMateriaPrima;
    }

    @XmlTransient
    public double getQuantidadeProduzir() {
        return quantidadeProduzir;
    }
}