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
@DiscriminatorValue(value=TipoDeMensagem.Values.ESTORNO)
public class MensagemEstorno extends Mensagem implements AggregateRoot<MensagemID> {
    @XmlElement(name = "deposito")
    public final CodigoDeposito codigo;

    @XmlElement(name = "produto")
    public final CodigoUnico codigoUnico;

    @XmlElement(name = "quantidadeDeMateriaPrima")
    private double quantidadeProduzir;

    protected MensagemEstorno() {
        this.codigo = null;
        this.codigoUnico = null;
    }

    public MensagemEstorno(CodigoUnico codigoUnico, CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, double quantidadeProduzir) {
        super(TipoDeMensagem.ESTORNO, new TimestampEmissao(dataHora), codigoInternoMaquina);
        if (codigoUnico == null || dataHora == null)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.codigoUnico = codigoUnico;
        this.codigo = codigo;
        this.quantidadeProduzir = quantidadeProduzir;
    }

    @XmlTransient
    public double getQuantidadeProduzir() {
        return quantidadeProduzir;
    }
}