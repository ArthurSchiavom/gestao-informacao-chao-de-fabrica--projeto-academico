package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class RetomaDeExecucao implements ValueObject,Comparable<RetomaDeExecucao> {
    private static final long serialVersionUID = 1L;

    private Date retomaDeExecucao;

    protected RetomaDeExecucao(){
        retomaDeExecucao=null;
    }

    public RetomaDeExecucao(String data,String tempo) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dataTempo=data+" "+tempo;
        this.retomaDeExecucao=format.parse(dataTempo);
    }

    @Override
    public int compareTo(RetomaDeExecucao o) {
        return this.retomaDeExecucao.compareTo(o.retomaDeExecucao);
    }
}
