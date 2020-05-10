package eapli.base.definircategoriamaterial.domain;

import eapli.base.infrastructure.application.HasDTO;
import eapli.base.utilities.Reflection;
<<<<<<< Updated upstream
import eapli.base.materiaprima.domain.UnidadeDeMedida;
=======
>>>>>>> Stashed changes
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
public class Material implements AggregateRoot<CodigoInterno>, HasDTO<MaterialDTO> {

    @Version
    private Long version;

    @EmbeddedId
    public CodigoInterno codigoInterno;
    public static String identityAttributeName(){
        return Reflection.retrieveAttributeName(Material.class, CodigoInterno.class);
    }

    public Descricao descricao;

    protected Material() {
    }

    protected Material(CodigoInterno codigoInterno, Descricao descricao) {
        this.codigoInterno = codigoInterno;
        this.descricao = descricao;
    }

    public Material valueOf(CodigoInterno codigoInterno, Descricao descricao) {
        return new Material(codigoInterno, descricao);
    }

    @Override
    public boolean equals(final Object o){
            return DomainEntities.areEqual(this,o);
    }

    @Override
    public int hashCode(){
            return DomainEntities.hashCode(this);
            }
    @Override
    public boolean sameAs(final Object other){
            return DomainEntities.areEqual(this,other);
            }

    @Override
    public CodigoInterno identity() {
        return this.codigoInterno;
    }

    @Override
    public MaterialDTO toDTO() {
        return new MaterialDTO(codigoInterno.codigoInterno, descricao.descricaoValor);
    }
}