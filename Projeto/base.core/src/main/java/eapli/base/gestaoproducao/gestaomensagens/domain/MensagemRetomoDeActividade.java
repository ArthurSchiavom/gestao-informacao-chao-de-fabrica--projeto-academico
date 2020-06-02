package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.RETOMA_ATIVIDADE)
public class MensagemRetomoDeActividade extends Mensagem {
    //S1 -> Máquina;TipoMsg;DataHora;Erro
    public final Date dataHora;
    public final String erro;

    protected MensagemRetomoDeActividade() {
        this.dataHora = null;
        this.erro = null;
    }

    public MensagemRetomoDeActividade(CodigoInternoMaquina codigoInternoMaquina, Date dataHora, String erro) {
        super(TipoDeMensagem.RETOMA_ATIVIDADE,new TimestampEmissao(dataHora),codigoInternoMaquina);
        if ( dataHora ==null && erro==null)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.dataHora = dataHora;
        this.erro = erro;
    }
}
