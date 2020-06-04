package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value = TipoDeMensagem.Values.PARAGEM_FORCADA)
public class MensagemParagemForcada extends Mensagem implements AggregateRoot<MensagemID> {
    //S8 -> Máquina;TipoMsg;DataHora


    protected  MensagemParagemForcada(){
        super();
    }


    public MensagemParagemForcada(CodigoInternoMaquina maquinaID, Date dataHora) {
        super(TipoDeMensagem.PARAGEM_FORCADA,new TimestampEmissao(dataHora),maquinaID);
    }
}
