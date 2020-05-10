package eapli.base.produto.domain;

import eapli.base.infrastructure.application.HasDTO;
import eapli.base.infrastructure.domain.IllegalDomainValue;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.materiaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.produto.application.FichaDeProducaoDTO;
import eapli.base.produto.application.QuantidadeDeMateriaPrimaDTO;
import eapli.base.utilities.Lists;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FichaDeProducao implements AggregateRoot<Integer>, HasDTO<FichaDeProducaoDTO> {

    @Version
    private Long version;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Integer uniqueVal;
    public static String identityAttributeName() {
        return "uniqueVal";
    }

    @ElementCollection
    @CollectionTable
    public List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima;

    public FichaDeProducao() {
    }

    protected FichaDeProducao(List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima) throws IllegalDomainValue {
        if (thisIsValid()) {
            throw new IllegalDomainValue("A lista de quantidades de matéria prima não pode ser nula ou ter menos de 1 elemento.", IllegalDomainValueType.ALREADY_EXISTS);
        }
        this.quantidadesDeMateriaPrima = quantidadesDeMateriaPrima;
    }

    public boolean thisIsValid() {
        return quantidadesDeMateriaPrima == null || quantidadesDeMateriaPrima.size() < 1;
    }

    public static FichaDeProducao valueOf(List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima) throws IllegalDomainValue {
        return new FichaDeProducao(quantidadesDeMateriaPrima);
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Integer identity() {
        return this.uniqueVal;
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
