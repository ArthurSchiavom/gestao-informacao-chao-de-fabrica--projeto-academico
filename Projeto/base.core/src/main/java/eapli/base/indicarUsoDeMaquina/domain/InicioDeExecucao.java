package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class InicioDeExecucao implements ValueObject ,Comparable<InicioDeExecucao>{
    private static final long serialVersionUID = 1L;

    private Date dataTempoInicio;

    protected InicioDeExecucao(){
        dataTempoInicio =null;
    }

    public InicioDeExecucao(String data,String tempo) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dataTempo=data+" "+tempo;
        this.dataTempoInicio =format.parse(dataTempo);
    }

    @Override
    public int compareTo(InicioDeExecucao o) {
        return this.dataTempoInicio.compareTo(o.dataTempoInicio);
    }
}
