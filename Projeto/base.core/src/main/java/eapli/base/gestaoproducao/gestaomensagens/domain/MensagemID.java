package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.framework.domain.model.ValueObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MensagemID implements ValueObject, Serializable, Comparable<MensagemID>{

    @Column(insertable=false, updatable=false)
    public final TipoDeMensagem tipoDeMensagem;
    @Column(insertable=false, updatable=false)
    public final TimestampEmissao tempoEmissao;

    public MensagemID() {
        tipoDeMensagem = null;
        tempoEmissao = null;
    }

    public MensagemID(TipoDeMensagem tipoDeMensagem, TimestampEmissao tempoEmissao) {
        if(tipoDeMensagem == null || tempoEmissao == null){
            throw new IllegalArgumentException("Campos da mensagemID não podem ser  nulos");
        }
        this.tipoDeMensagem = tipoDeMensagem;
        this.tempoEmissao = tempoEmissao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensagemID that = (MensagemID) o;
        return tipoDeMensagem == that.tipoDeMensagem &&
                tempoEmissao.equals(that.tempoEmissao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoDeMensagem, tempoEmissao);
    }

    @Override
    public int compareTo(MensagemID mensagemID) {
        return this.tempoEmissao.timestamp.compareTo(mensagemID.tempoEmissao.timestamp);
    }
}
