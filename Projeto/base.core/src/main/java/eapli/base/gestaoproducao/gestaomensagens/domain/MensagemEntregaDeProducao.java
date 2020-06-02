package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.application.IdentificadorDeLote;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.ENTREGA_DE_PRODUCAO)
public class MensagemEntregaDeProducao extends Mensagem implements AggregateRoot<Long> {
    public final CodigoDeposito codigo;
    public final Date dataHora;
    private final IdentificadorDeLote identificadorDeLote;
    private int quantidadeProduzir;

    protected MensagemEntregaDeProducao() {
        this.codigo = null;
        this.dataHora = null;
        this.identificadorDeLote=null;
    }

    public MensagemEntregaDeProducao(CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, int quantidadeProduzir, IdentificadorDeLote identificadorDeLote) {
        super(TipoDeMensagem.ENTREGA_DE_PRODUCAO, new TimestampEmissao(dataHora),codigoInternoMaquina);
        if (codigo == null && dataHora == null && quantidadeProduzir<=0 )
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.identificadorDeLote=identificadorDeLote;
        this.codigo = codigo;
        this.dataHora = dataHora;
        this.quantidadeProduzir = quantidadeProduzir;
    }

}
