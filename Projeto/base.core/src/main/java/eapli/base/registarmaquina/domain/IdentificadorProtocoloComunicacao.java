package eapli.base.registarmaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class IdentificadorProtocoloComunicacao implements ValueObject, Comparable<IdentificadorProtocoloComunicacao> {

    private static final long serialVersionUID = 1L;

    public final String identificadorProtocoloComunicao;

    protected IdentificadorProtocoloComunicacao() {
        identificadorProtocoloComunicao = null;
    }

    public IdentificadorProtocoloComunicacao(String identificadorProtocoloComunicao) throws IllegalArgumentException{
        if(identificadorProtocoloComunicao == null || identificadorProtocoloComunicao.trim().isEmpty()){
            throw new IllegalArgumentException("Identificador protocolo de comunocação inválido");
        }
        this.identificadorProtocoloComunicao = identificadorProtocoloComunicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentificadorProtocoloComunicacao that = (IdentificadorProtocoloComunicacao) o;
        return identificadorProtocoloComunicao.equals(that.identificadorProtocoloComunicao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificadorProtocoloComunicao);
    }

    @Override
    public String toString() {
        return "IdentificadorProtocoloComunicacao{" +
                "identificadorProtocoloComunicao='" + identificadorProtocoloComunicao + '\'' +
                '}';
    }

    @Override
    public int compareTo(IdentificadorProtocoloComunicacao obj) {
        return this.identificadorProtocoloComunicao.compareTo(obj.identificadorProtocoloComunicao);
    }
}