package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.IdentificadorDeLote;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.ENTREGA_DE_PRODUCAO)
public class MensagemEntregaDeProducao extends Mensagem implements AggregateRoot<MensagemID> {
    public final CodigoDeposito codigo;
    private final IdentificadorDeLote identificadorDeLote;
    private double quantidadeATransferir;

    protected MensagemEntregaDeProducao() {
        this.codigo = null;
        this.identificadorDeLote=null;
    }

    public MensagemEntregaDeProducao(CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, double  quantidadeATransferir, IdentificadorDeLote identificadorDeLote) {
        super(TipoDeMensagem.ENTREGA_DE_PRODUCAO, new TimestampEmissao(dataHora),codigoInternoMaquina);
        if (codigo == null  || quantidadeATransferir<=0 )
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.identificadorDeLote=identificadorDeLote;
        this.codigo = codigo;
        this.quantidadeATransferir = quantidadeATransferir;
    }

    public double getQuantidadeATransferir() {
        return quantidadeATransferir;
    }

}
