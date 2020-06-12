package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class TempoEfetivoExecucao implements ValueObject {

    private Date tempoEfetivoExecucao;


    public TempoEfetivoExecucao() {
        tempoEfetivoExecucao=null;
    }



}
