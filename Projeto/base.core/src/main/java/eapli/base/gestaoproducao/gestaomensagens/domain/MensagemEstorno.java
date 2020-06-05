package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.ESTORNO)
public class MensagemEstorno extends Mensagem implements AggregateRoot<MensagemID> {
    public final CodigoDeposito codigo;
    public final CodigoUnico codigoUnico;
    private int quantidadeProduzir;

    protected MensagemEstorno() {
        this.codigo = null;
        this.codigoUnico = null;
    }

    public MensagemEstorno(CodigoUnico codigoUnico, CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, int quantidadeProduzir) {
        super(TipoDeMensagem.ESTORNO, new TimestampEmissao(dataHora), codigoInternoMaquina);
        if (codigoUnico == null && dataHora == null)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.codigoUnico = codigoUnico;
        this.codigo = codigo;
        this.quantidadeProduzir = quantidadeProduzir;
    }

    public int getQuantidadeProduzir() {
        return quantidadeProduzir;
    }
}