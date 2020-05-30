package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.application.IdentificadorDeLote;
import eapli.base.gestaoproducao.ordemProducao.domain.Identificador;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.ENTREGA_DE_PRODUCAO)
public class MensagemEntregaDeProducao extends Mensagem implements AggregateRoot<Long> {
    private final CodigoDeposito codigo;
    public final CodigoInternoMaquina maquinaID;
    private final CodigoInternoMaquina codigoInternoMaquina;
    public final Date dataHora;
    private final IdentificadorDeLote identificadorDeLote;
    private int quantidadeProduzir;

    protected MensagemEntregaDeProducao() {
        this.codigo = null;
        this.maquinaID = null;
        this.codigoInternoMaquina = null;
        this.dataHora = null;
        this.identificadorDeLote=null;
    }

    public MensagemEntregaDeProducao(CodigoDeposito codigo, CodigoInternoMaquina maquinaID, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, int quantidadeProduzir, IdentificadorDeLote identificadorDeLote) {
        super(TipoDeMensagem.ENTREGA_DE_PRODUCAO, new TimestampEmissao(dataHora));
        if (maquinaID == null && codigoInternoMaquina == null && dataHora == null && quantidadeProduzir <= 0)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.identificadorDeLote=identificadorDeLote;
        this.codigo = codigo;
        this.maquinaID = maquinaID;
        this.codigoInternoMaquina = codigoInternoMaquina;
        this.dataHora = dataHora;
        this.quantidadeProduzir = quantidadeProduzir;
    }
}
