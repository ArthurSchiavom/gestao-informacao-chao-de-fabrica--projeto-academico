package eapli.base.gestaoproducao.ordemProducao.domain;

import com.google.common.math.DoubleMath;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.framework.domain.model.ValueObject;

import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

public class QuantidadeAProduzir implements ValueObject, Comparable<QuantidadeAProduzir>{

    private static final long serialVersionUID = 1L;

    @XmlValue
    public final double quantidade;

    private QuantidadeAProduzir() {
        quantidade = 0;
    }

    public QuantidadeAProduzir(double quantidade) throws IllegalDomainValueException {
        if (quantidade <= 0) {
            throw new IllegalDomainValueException("A quantidade de produto que uma ordem deve produzir tem que ser maior que 0.", IllegalDomainValueType.ILLEGAL_VALUE);
        }

        this.quantidade = quantidade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuantidadeAProduzir that = (QuantidadeAProduzir) o;
        return quantidade == that.quantidade;
    }


    @Override
    public int hashCode() {
        return Objects.hash(quantidade);
    }

    @Override
    public String toString() {
        return "QuantidadeAProduzir{" +
                "quantidade=" + quantidade +
                '}';
    }

    @Override
    public int compareTo(QuantidadeAProduzir outro) {
        return DoubleMath.fuzzyCompare(this.quantidade, outro.quantidade, 0.00000001d);
    }
}
