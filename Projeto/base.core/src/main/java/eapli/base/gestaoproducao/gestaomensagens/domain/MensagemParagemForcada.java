package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value = TipoDeMensagem.Values.PARAGEM_FORCADA)
public class MensagemParagemForcada extends Mensagem {
    //S8 -> Máquina;TipoMsg;DataHora
    public final Date dataHora;


    protected  MensagemParagemForcada() {
        this.dataHora = null;
    }


    public MensagemParagemForcada(CodigoInternoMaquina maquinaID, Date dataHora) {
        super(TipoDeMensagem.PARAGEM_FORCADA,new TimestampEmissao(dataHora),maquinaID);
        if (dataHora==null )
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.dataHora = dataHora;
    }
}
