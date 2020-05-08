package eapli.base.materiaprima.produto.domain;

import eapli.base.infrastructure.application.DTO;
import eapli.base.materiaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.materiaprima.produto.application.FichaDeProducaoDTO;
import eapli.base.materiaprima.produto.application.QuantidadeDeMateriaPrimaDTO;
import eapli.base.utilities.Lists;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class FichaDeProducao implements ValueObject, Serializable, DTO<FichaDeProducaoDTO> {

    private static final long serialVersionUID = 1L;

    @ElementCollection
    @CollectionTable
    private final List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima;

    protected FichaDeProducao() {
        quantidadesDeMateriaPrima = null;
    }

    protected void addQuantidadeDeMateriaPrima(QuantidadeDeMateriaPrima quantidadeDeMateriaPrima) {
        if (quantidadeDeMateriaPrima == null){
            throw new IllegalArgumentException("A quantidade de matéria prima não pode ser null");
        }
        quantidadesDeMateriaPrima.add(quantidadeDeMateriaPrima);
    }

    public static FichaDeProducao valueOf() {
        return new FichaDeProducao();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FichaDeProducao)) {
            return false;
        }

        final FichaDeProducao that = (FichaDeProducao) o;

        return Lists.equals(this.quantidadesDeMateriaPrima, that.quantidadesDeMateriaPrima);
    }

    @Override
    public int hashCode() {
        return quantidadesDeMateriaPrima.hashCode();
    }

    @Override
    public String toString() {
        if (quantidadesDeMateriaPrima.isEmpty())
            return "Ficha de produção vazia";

        return Lists.generateColonSeparatedDisplayList(quantidadesDeMateriaPrima);
    }

    @Override
    public FichaDeProducaoDTO toDTO() {
        List<QuantidadeDeMateriaPrimaDTO> resultado = new ArrayList<>();
        for (QuantidadeDeMateriaPrima quantidade : this.quantidadesDeMateriaPrima) {
            resultado.add(quantidade.toDTO());
        }
        return new FichaDeProducaoDTO(resultado);
    }
}

