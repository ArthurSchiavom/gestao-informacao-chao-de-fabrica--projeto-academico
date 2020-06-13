package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import java.util.Date;

public class RetomaExecucao implements ValueObject {

    private static final long serialVersionUID = 1L;

    public final Date dataRetomaExecucao;

    protected RetomaExecucao(){
        dataRetomaExecucao =null;
    }

    public RetomaExecucao(Date date) {
        this.dataRetomaExecucao =date;
    }
}
