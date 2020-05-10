package eapli.base.produto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class QuantidadeZeroMais implements ValueObject, Comparable<QuantidadeZeroMais> {

    private static final long serialVersionUID = 1L;

    /* O hibernate falha se for final. Isto acontece a todos os valores de Embeddables que
    * pertençam outro Embeddable, que por sua vez façam parte de uma @ElementCollection + @CollectionTable
    * */
    public double quantidadeValor;

    protected QuantidadeZeroMais() {
        quantidadeValor = 0;
    }

    protected QuantidadeZeroMais(double quantidade) throws IllegalDomainValueException {
        if (quantidade <= 0) {
            throw new IllegalDomainValueException("A quantidade deve ser maior que 0", IllegalDomainValueType.ILLEGAL_VALUE);
        }
        this.quantidadeValor = quantidade;
    }

    public static QuantidadeZeroMais valueOf(double quantidade) throws IllegalDomainValueException {
        return new QuantidadeZeroMais(quantidade);
    }

    /* O hibernate falha se não tiver getters e setters */
    public double getQuantidadeValor() {
        return quantidadeValor;
    }

    /* O hibernate falha se não tiver getters e setters */
    public void setQuantidadeValor(double quantidadeValor) {
        this.quantidadeValor = quantidadeValor;
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

