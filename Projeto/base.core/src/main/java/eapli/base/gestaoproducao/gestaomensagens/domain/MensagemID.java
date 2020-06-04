package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MensagemID implements ValueObject, Serializable, Comparable<MensagemID>{

    public final CodigoInternoMaquina codigoInternoMaquina;
    @Enumerated(EnumType.STRING)
    @Column(insertable=false, updatable=false)
    public final TipoDeMensagem tipoDeMensagem;
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
