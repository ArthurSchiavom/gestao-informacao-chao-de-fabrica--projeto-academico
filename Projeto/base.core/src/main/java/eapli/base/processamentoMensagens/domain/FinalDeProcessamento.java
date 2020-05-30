package eapli.base.processamentoMensagens.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class FinalDeProcessamento implements ValueObject,Comparable<FinalDeProcessamento> {
    private static final long serialVersionUID = 1L;

    @XmlValue
    public final Date dataTempoFinal;

    protected FinalDeProcessamento(){
        dataTempoFinal=null;
    }

    public FinalDeProcessamento(String data,String tempo) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String dataTempo=data+'T'+tempo;
        this.dataTempoFinal=format.parse(dataTempo);
    }

    @Override
    public int compareTo(FinalDeProcessamento o) {
        return this.dataTempoFinal.compareTo(o.dataTempoFinal);
    }

}
