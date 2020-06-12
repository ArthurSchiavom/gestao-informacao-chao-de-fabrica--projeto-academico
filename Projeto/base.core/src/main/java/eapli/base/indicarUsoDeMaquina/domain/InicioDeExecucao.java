package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class InicioDeExecucao implements ValueObject ,Comparable<InicioDeExecucao>{
    private static final long serialVersionUID = 1L;

    private Date dataTempoInicio;

    protected InicioDeExecucao(){
        dataTempoInicio =null;
    }

    public InicioDeExecucao(Date date) {
       this.dataTempoInicio=date;
    }

    @Override
    public int compareTo(InicioDeExecucao o) {
        return this.dataTempoInicio.compareTo(o.dataTempoInicio);
    }
}
