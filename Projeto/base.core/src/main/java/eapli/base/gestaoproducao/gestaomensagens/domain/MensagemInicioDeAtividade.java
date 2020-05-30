package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.ordemProducao.domain.Identificador;
import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.INICIO_DE_ATIVIDADE)
public class MensagemInicioDeAtividade extends Mensagem implements AggregateRoot<Long> {

    //Máquina;TipoMsg;DataHora;OrdemProducao
    public final CodigoInternoMaquina maquinaID;
    public final Date dataHora;
    private Identificador ordemID; // pode ser null

    protected MensagemInicioDeAtividade() {
        super();
        maquinaID = null;
        dataHora = null;
    }

    public MensagemInicioDeAtividade(TimestampEmissao tempoEmissao,
                                     CodigoInternoMaquina maquinaID, Date dataHora,
                                     Identificador ordemID) {
        super(TipoDeMensagem.INICIO_DE_ATIVIDADE, tempoEmissao);

        if(tempoEmissao == null || maquinaID == null || dataHora == null ){
            throw new IllegalArgumentException("Não pode haver parametros null no Inicio de atividade mensagem"); // excepto o Ordem id que pode ser nulo
        }
        this.maquinaID = maquinaID;
        this.dataHora = dataHora;
        this.ordemID = ordemID;
    }
}