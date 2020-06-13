package eapli.base.processamentoMensagens.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class InicioDeProcessamento implements ValueObject, Comparable<InicioDeProcessamento> {
    private static final long serialVersionUID = 1L;

    @XmlTransient
    public final Date dataTempoInicio;

    protected InicioDeProcessamento(){
        dataTempoInicio=null;
    }

    public InicioDeProcessamento(String data,String tempo) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dataTempo=data+" "+tempo;
        this.dataTempoInicio=format.parse(dataTempo);
    }

    @XmlValue
    private long getTempoEmEpoch() {
        return dataTempoInicio.getTime();
    }

    @Override
    public int compareTo(InicioDeProcessamento o) {
        return this.dataTempoInicio.compareTo(o.dataTempoInicio);
    }
}
