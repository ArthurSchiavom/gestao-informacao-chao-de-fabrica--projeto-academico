package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class PausaDeExecucao implements ValueObject, Comparable<PausaDeExecucao> {
    private static final long serialVersionUID = 1L;

    private Date pausaDeExecucao;

    protected PausaDeExecucao(){
        pausaDeExecucao=null;
    }

    public PausaDeExecucao(String data,String tempo) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dataTempo=data+" "+tempo;
        this.pausaDeExecucao=format.parse(dataTempo);
    }

    @Override
    public int compareTo(PausaDeExecucao o) {
        return this.pausaDeExecucao.compareTo(o.pausaDeExecucao);
    }
}
