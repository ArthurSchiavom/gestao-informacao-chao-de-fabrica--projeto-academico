package eapli.base.produto.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class QuantidadeZeroMais implements ValueObject, Comparable<QuantidadeZeroMais> {

    private static final long serialVersionUID = 1L;

    public final double quantidadeValor;

    public QuantidadeZeroMais() {
        quantidadeValor = 0;
    }

    public QuantidadeZeroMais(double quantidade) {
        this.quantidadeValor = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuantidadeZeroMais)) {
            return false;
        }

        final QuantidadeZeroMais that = (QuantidadeZeroMais) o;

        return quantidadeValor == that.quantidadeValor;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(quantidadeValor);
    }

    @Override
    public String toString() {
        return "" + quantidadeValor;
    }

    @Override
    public int compareTo(QuantidadeZeroMais obj) {
        return Double.compare(quantidadeValor, obj.quantidadeValor);
    }
}

