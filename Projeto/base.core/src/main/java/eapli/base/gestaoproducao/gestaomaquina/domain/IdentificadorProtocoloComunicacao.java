package eapli.base.gestaoproducao.gestaomaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Objects;

@Embeddable
public class IdentificadorProtocoloComunicacao implements ValueObject, Comparable<IdentificadorProtocoloComunicacao> {

    @Transient
    private final int MAX_RANGE = 65535;
    @Transient
    private final int MIN_RANGE = 1;
    private static final long serialVersionUID = 1L;

    public final int identificadorProtocoloComunicao;

    protected IdentificadorProtocoloComunicacao() {
        identificadorProtocoloComunicao = 0;
    }

    public IdentificadorProtocoloComunicacao(int identificadorProtocoloComunicao) throws IllegalArgumentException{
        if( identificadorProtocoloComunicao > MAX_RANGE || identificadorProtocoloComunicao < MIN_RANGE){
            throw new IllegalArgumentException("Identificador protocolo de comunicação inválido");
        }
        this.identificadorProtocoloComunicao = identificadorProtocoloComunicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentificadorProtocoloComunicacao that = (IdentificadorProtocoloComunicacao) o;
        return identificadorProtocoloComunicao == that.identificadorProtocoloComunicao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificadorProtocoloComunicao);
    }

    @Override
    public String toString() {
        return "Identificador Protocolo Comunicao='" + identificadorProtocoloComunicao + '\'' +
                '}';
    }

    @Override
    public int compareTo(IdentificadorProtocoloComunicacao obj) {
        return this.identificadorProtocoloComunicao-obj.identificadorProtocoloComunicao;
    }
}