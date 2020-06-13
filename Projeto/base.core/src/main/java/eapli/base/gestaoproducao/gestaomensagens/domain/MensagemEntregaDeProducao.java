package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.IdentificadorDeLote;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.ENTREGA_DE_PRODUCAO)
public class MensagemEntregaDeProducao extends Mensagem implements AggregateRoot<MensagemID> {
    public final CodigoDeposito codigo;
    private final IdentificadorDeLote identificadorDeLote;
    public final CodigoUnico codigoUnico;
    private double quantidadeATransferir;

    protected MensagemEntregaDeProducao() {
        this.codigo = null;
        this.identificadorDeLote=null;
        this.codigoUnico=null;
    }

    public MensagemEntregaDeProducao(CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, double  quantidadeATransferir, IdentificadorDeLote identificadorDeLote,CodigoUnico codigoUnico) {
        super(TipoDeMensagem.ENTREGA_DE_PRODUCAO, new TimestampEmissao(dataHora),codigoInternoMaquina);
        if (codigo == null||codigoUnico==null || quantidadeATransferir<=0 )
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.identificadorDeLote=identificadorDeLote;
        this.codigo = codigo;
        this.quantidadeATransferir = quantidadeATransferir;
        this.codigoUnico=codigoUnico;
    }

    public double getQuantidadeATransferir() {
        return quantidadeATransferir;
    }

}
