package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.RETOMA_ATIVIDADE)
public class MensagemRetomoDeActividade extends Mensagem implements AggregateRoot<MensagemID> {
    //S1 -> Máquina;TipoMsg;DataHora;Erro
    public final String erro;

    protected MensagemRetomoDeActividade() {
        this.erro = null;
    }

    public MensagemRetomoDeActividade(CodigoInternoMaquina codigoInternoMaquina, Date dataHora, String erro) {
        super(TipoDeMensagem.RETOMA_ATIVIDADE,new TimestampEmissao(dataHora),codigoInternoMaquina);
        if ( erro==null)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.erro = erro;
    }
}
