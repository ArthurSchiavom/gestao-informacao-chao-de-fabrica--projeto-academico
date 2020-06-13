package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import java.util.Date;

public class PausaDeExecucao implements ValueObject {

    private static final long serialVersionUID = 1L;

    public final Date dataPausaDeExecucao;

    protected PausaDeExecucao(){
        dataPausaDeExecucao =null;
    }

    public PausaDeExecucao(Date date) {
        this.dataPausaDeExecucao=date;
    }
}
