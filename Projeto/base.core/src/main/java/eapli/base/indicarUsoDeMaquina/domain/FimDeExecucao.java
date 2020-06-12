package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class FimDeExecucao implements ValueObject, Comparable<FimDeExecucao> {
    private static final long serialVersionUID = 1L;

    @Column(insertable=false, updatable=false)
    private Date dataTempoFinal;

    protected FimDeExecucao(){
        dataTempoFinal =null;
    }

    public FimDeExecucao(Date date)  {
        this.dataTempoFinal=date;
    }

    @Override
    public int compareTo(FimDeExecucao o) {
        return this.dataTempoFinal.compareTo(o.dataTempoFinal);
    }
}
