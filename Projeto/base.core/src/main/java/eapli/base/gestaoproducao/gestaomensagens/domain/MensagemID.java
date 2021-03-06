package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MensagemID implements ValueObject, Serializable, Comparable<MensagemID>{

    @XmlElement(name="maquina")
    public final CodigoInternoMaquina codigoInternoMaquina;

    @XmlTransient
    @Enumerated(EnumType.STRING)
    @Column(insertable=false, updatable=false)
    public final TipoDeMensagem tipoDeMensagem;

    @XmlElement(name = "timestampEmissaoEpochMilli")
    @Column(insertable=false, updatable=false)
    public final TimestampEmissao tempoEmissao;


    public MensagemID() {
        tipoDeMensagem = null;
        tempoEmissao = null;
        codigoInternoMaquina=null;
    }

    public MensagemID(TipoDeMensagem tipoDeMensagem, TimestampEmissao tempoEmissao,CodigoInternoMaquina codigoInternoMaquina) {
        if(tipoDeMensagem == null || tempoEmissao == null || codigoInternoMaquina==null){
            throw new IllegalArgumentException("Campos da mensagemID não podem ser  nulos");
        }
        this.tipoDeMensagem = tipoDeMensagem;
        this.tempoEmissao = tempoEmissao;
        this.codigoInternoMaquina=codigoInternoMaquina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensagemID that = (MensagemID) o;
        return tipoDeMensagem == that.tipoDeMensagem &&
                tempoEmissao.equals(that.tempoEmissao) && codigoInternoMaquina.equals(that.codigoInternoMaquina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoDeMensagem, tempoEmissao,codigoInternoMaquina);
    }

    @Override
    public int compareTo(MensagemID mensagemID) {
        return this.tempoEmissao.timestamp.compareTo(mensagemID.tempoEmissao.timestamp);
    }
}
