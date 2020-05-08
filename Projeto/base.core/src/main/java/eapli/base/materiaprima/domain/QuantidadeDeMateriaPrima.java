package eapli.base.producao.materiaprima.domain;

import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.infrastructure.application.DTO;
import eapli.base.producao.produto.application.QuantidadeDeMateriaPrimaDTO;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuantidadeDeMateriaPrima implements ValueObject, Serializable, Comparable<QuantidadeDeMateriaPrima>, DTO<QuantidadeDeMateriaPrimaDTO> {

    private static final long serialVersionUID = 1L;

    private final double quantidade;
    public final MateriaPrima materiaPrima;

    public QuantidadeDeMateriaPrima() {
        this.quantidade = 0;
        materiaPrima = null;
    }

    public QuantidadeDeMateriaPrima(double quantidade, MateriaPrima materiaPrima) {
        this.quantidade = quantidade;
        this.materiaPrima = materiaPrima;
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

        return this.quantidade == that.quantidade && this.materiaPrima.equals(that.materiaPrima);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantidade, materiaPrima);
    }

    @Override
    public String toString() {
        return quantidade + " de " + materiaPrima.toString();
    }

    @Override
    public int compareTo(QuantidadeDeMateriaPrima obj) {
        return Double.compare(this.quantidade, obj.quantidade);
    }

    @Override
    public QuantidadeDeMateriaPrimaDTO toDTO() {
        return new QuantidadeDeMateriaPrimaDTO(materiaPrima.toDTO(), quantidade);
    }
}

