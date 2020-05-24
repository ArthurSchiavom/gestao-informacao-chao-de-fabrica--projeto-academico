package eapli.base.gestaoproducao.medicao;

import com.google.common.math.DoubleMath;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

@Embeddable
public class QuantidadePositiva implements ValueObject, Comparable<QuantidadePositiva> {

    private static final long serialVersionUID = 1L;
    private static final double TOLERANCIA_DOUBLE = 0.000000001d;

    /* O hibernate falha se for final. Isto acontece a todos os valores de Embeddables que
    * pertençam outro Embeddable, que por sua vez façam parte de uma @ElementCollection + @CollectionTable
    * */
    @XmlValue
    public double quantidadeValor;

    protected QuantidadePositiva() {
        quantidadeValor = 0;
    }

    /* O hibernate falha se não tiver getters e setters públicos */
    @XmlTransient
    public double getQuantidadeValor() {
        return quantidadeValor;
    }

    /* O hibernate falha se não tiver getters e setters públicos */
    public void setQuantidadeValor(double quantidadeValor) {
        this.quantidadeValor = quantidadeValor;
    }

    protected QuantidadePositiva(double quantidade) throws IllegalDomainValueException {
        if (quantidade <= 0) {
            throw new IllegalDomainValueException("A quantidade deve ser maior que 0", IllegalDomainValueType.ILLEGAL_VALUE);
        }
        this.quantidadeValor = quantidade;
    }

    public static QuantidadePositiva valueOf(double quantidade) throws IllegalDomainValueException {
        return new QuantidadePositiva(quantidade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuantidadePositiva)) {
            return false;
        }

        final QuantidadePositiva that = (QuantidadePositiva) o;

        return DoubleMath.fuzzyEquals(quantidadeValor, that.quantidadeValor, TOLERANCIA_DOUBLE);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(quantidadeValor);
    }

    @Override
    public String toString() {
        return String.format("%.4f", quantidadeValor);
    }

    @Override
    public int compareTo(QuantidadePositiva obj) {
        return DoubleMath.fuzzyCompare(quantidadeValor, obj.quantidadeValor, TOLERANCIA_DOUBLE);
    }
}

