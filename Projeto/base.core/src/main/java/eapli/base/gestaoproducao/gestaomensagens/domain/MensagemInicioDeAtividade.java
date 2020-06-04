package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.ordemProducao.domain.Identificador;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.INICIO_DE_ATIVIDADE)
public class MensagemInicioDeAtividade extends Mensagem implements AggregateRoot<MensagemID> {

    //Máquina;TipoMsg;DataHora;OrdemProducao

    protected MensagemInicioDeAtividade() {
        super();
    }

    public MensagemInicioDeAtividade(Date tempoEmissao, CodigoInternoMaquina maquinaID, Identificador ordem) {
        super(TipoDeMensagem.INICIO_DE_ATIVIDADE, new TimestampEmissao(tempoEmissao),maquinaID,ordem);
    }
}