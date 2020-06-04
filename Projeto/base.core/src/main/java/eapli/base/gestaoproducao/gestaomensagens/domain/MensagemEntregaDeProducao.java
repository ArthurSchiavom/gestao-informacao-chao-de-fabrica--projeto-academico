package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.application.IdentificadorDeLote;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.ENTREGA_DE_PRODUCAO)
public class MensagemEntregaDeProducao extends Mensagem implements AggregateRoot<MensagemID> {
    public final CodigoDeposito codigo;
    private final IdentificadorDeLote identificadorDeLote;
    private int quantidadeProduzir;

    protected MensagemEntregaDeProducao() {
        this.codigo = null;
        this.identificadorDeLote=null;
    }

    public MensagemEntregaDeProducao(CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, int quantidadeProduzir, IdentificadorDeLote identificadorDeLote) {
        super(TipoDeMensagem.ENTREGA_DE_PRODUCAO, new TimestampEmissao(dataHora),codigoInternoMaquina);
        if (codigo == null  && quantidadeProduzir<=0 )
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.identificadorDeLote=identificadorDeLote;
        this.codigo = codigo;
        this.quantidadeProduzir = quantidadeProduzir;
    }

    public int getQuantidadeProduzir() {
        return quantidadeProduzir;
    }
}
