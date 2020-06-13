package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.FichaDeProducaoDTO;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.QuantidadeDeMateriaPrimaDTO;
import eapli.base.infrastructure.application.ConvertableToDTO;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.utilities.Lists;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FichaDeProducao implements AggregateRoot<Integer>, ConvertableToDTO<FichaDeProducaoDTO> {

    @Version
    private Long version;

    private final String fichaVaziaMensagem = "Ficha de produção vazia";

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @XmlAttribute
    public final Integer uniqueVal;
    public static String identityAttributeName() {
        return "uniqueVal";
    }

    @ElementCollection
    @CollectionTable
    @XmlElement
    public List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima;

    protected FichaDeProducao() {
        uniqueVal = 0;
    }

    protected FichaDeProducao(List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima) throws IllegalDomainValueException {
        if (!thisIsValid(quantidadesDeMateriaPrima)) {
            throw new IllegalDomainValueException("A lista de quantidades de matéria prima deve existir e ter pelo menos um elemento.", IllegalDomainValueType.ALREADY_EXISTS);
        }
        this.quantidadesDeMateriaPrima = new ArrayList<>(quantidadesDeMateriaPrima);
        uniqueVal = 0;
    }

    private boolean thisIsValid(List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima) {
        return quantidadesDeMateriaPrima != null && quantidadesDeMateriaPrima.size() > 0;
    }

    public static FichaDeProducao valueOf(List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima) throws IllegalDomainValueException {
        return new FichaDeProducao(quantidadesDeMateriaPrima);
    }

    /**
     * Obtem uma materia prima por id
     * @param idMateriaPrima Id da materia prima
     * @return Retorna a materia prima caso o idMateriaPrima esteja associada com alguma da lista registada e null caso nao associe a nenhuma
     */
    public MateriaPrima obterMateriaPrimaPorID(String idMateriaPrima){
        for (QuantidadeDeMateriaPrima quantidadeDeMateriaPrima:quantidadesDeMateriaPrima){
            String idMateria=quantidadeDeMateriaPrima.materiaPrima.idMateria;
            if (idMateriaPrima.equals(idMateria)){
                return quantidadeDeMateriaPrima.materiaPrima;
            }
        }
        return null;
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
