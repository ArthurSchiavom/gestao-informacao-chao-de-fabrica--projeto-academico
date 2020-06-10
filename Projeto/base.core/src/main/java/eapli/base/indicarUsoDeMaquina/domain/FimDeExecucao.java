package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class FimDeExecucao implements ValueObject, Comparable<FimDeExecucao> {
    private static final long serialVersionUID = 1L;

    @Column(insertable=false, updatable=false)
    private Date dataTempoFinal;

    protected FimDeExecucao(){
        dataTempoFinal =null;
    }

    public FimDeExecucao(String data,String tempo) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dataTempo=data+" "+tempo;
        this.dataTempoFinal =format.parse(dataTempo);
    }

    @Override
    public int compareTo(FimDeExecucao o) {
        return this.dataTempoFinal.compareTo(o.dataTempoFinal);
    }
}
