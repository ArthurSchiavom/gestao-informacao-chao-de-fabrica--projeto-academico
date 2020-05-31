package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import eapli.base.gestaoproducao.gestaoproduto.application.dto.QuantidadeDeMateriaPrimaDTO;
import eapli.base.gestaoproducao.medicao.QuantidadePositiva;
import eapli.base.infrastructure.application.ConvertableToDTO;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuantidadeDeMateriaPrima implements ValueObject, Serializable, Comparable<QuantidadeDeMateriaPrima>, ConvertableToDTO<QuantidadeDeMateriaPrimaDTO> {

    private static final long serialVersionUID = 1L;

    @XmlElement
    public final QuantidadePositiva quantidade;
    @XmlElement
    public final MateriaPrima materiaPrima;

    protected QuantidadeDeMateriaPrima() {
        quantidade = null;
        materiaPrima = null;
    }

    protected QuantidadeDeMateriaPrima(QuantidadePositiva quantidade, MateriaPrima materiaPrima) {
        if (quantidade == null || materiaPrima == null) {
            throw new IllegalArgumentException("Não são permitidos valores nulos");
        }

        this.quantidade = quantidade;
        this.materiaPrima = materiaPrima;
    }

    public static QuantidadeDeMateriaPrima valueOf(QuantidadePositiva quantidade, MateriaPrima materiaPrima) {
        return new QuantidadeDeMateriaPrima(quantidade, materiaPrima);
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

        return this.quantidade.equals(that.quantidade) && this.materiaPrima.equals(that.materiaPrima);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantidade, materiaPrima);
    }

    @Override
    public String toString() {
        return String.format("%s de %s", quantidade.toString(), materiaPrima.toString());
    }

    @Override
    public int compareTo(QuantidadeDeMateriaPrima obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public QuantidadeDeMateriaPrimaDTO toDTO() {
        return new QuantidadeDeMateriaPrimaDTO(materiaPrima.toDTO(), quantidade.quantidadeValor);
    }
}

