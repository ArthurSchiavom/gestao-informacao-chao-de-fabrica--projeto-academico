package eapli.base.producao.materiaprima.domain;

import eapli.base.producao.materiaprima.material.domain.Material;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuantidadeDeMateriaPrima implements ValueObject, Serializable, Comparable<QuantidadeDeMateriaPrima> {

    private static final long serialVersionUID = 1L;

    private final double quantidade;
    @OneToOne
    private final Material material;

    public QuantidadeDeMateriaPrima() {
        this.quantidade = 0;
        material = null;
    }

    public QuantidadeDeMateriaPrima(double quantidade, UnidadeDeMedida unidadeDeMedida, Material material) {
        this.quantidade = quantidade;
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuantidadeDeMateriaPrima)) {
            return false;
        }

        final QuantidadeDeMateriaPrima that = (QuantidadeDeMateriaPrima) o;

        return this.quantidade == that.quantidade && this.material.equals(that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantidade, material);
    }

    @Override
    public String toString() {
        return quantidade + " de " + material.toString();
    }

    @Override
    public int compareTo(QuantidadeDeMateriaPrima obj) {
        return Double.compare(this.quantidade, obj.quantidade);
    }
}

