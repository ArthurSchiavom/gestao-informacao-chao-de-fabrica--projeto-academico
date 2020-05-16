package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import eapli.base.gestaoproducao.gestaoproduto.application.dto.QuantidadeDeMateriaPrimaDTO;
import eapli.base.gestaoproducao.gestaoproduto.domain.QuantidadeZeroMais;
import eapli.base.infrastructure.application.HasDTO;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuantidadeDeMateriaPrima implements ValueObject, Serializable, Comparable<QuantidadeDeMateriaPrima>, HasDTO<QuantidadeDeMateriaPrimaDTO> {

    private static final long serialVersionUID = 1L;

    public final QuantidadeZeroMais quantidade;
    public final MateriaPrima materiaPrima;

    protected QuantidadeDeMateriaPrima() {
        quantidade = null;
        materiaPrima = null;
    }

    protected QuantidadeDeMateriaPrima(QuantidadeZeroMais quantidade, MateriaPrima materiaPrima) {
        this.quantidade = quantidade;
        this.materiaPrima = materiaPrima;
    }

    public static QuantidadeDeMateriaPrima valueOf(QuantidadeZeroMais quantidade, MateriaPrima materiaPrima) {
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
        return quantidade.toString() + " de " + materiaPrima.toString();
    }

    @Override
    public int compareTo(QuantidadeDeMateriaPrima obj) {
        return quantidade.compareTo(obj.quantidade);
    }

    @Override
    public QuantidadeDeMateriaPrimaDTO toDTO() {
        return new QuantidadeDeMateriaPrimaDTO(materiaPrima.toDTO(), quantidade.quantidadeValor);
    }
}

