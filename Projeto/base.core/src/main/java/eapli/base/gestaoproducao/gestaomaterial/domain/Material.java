
package eapli.base.gestaoproducao.gestaomaterial.domain;

import eapli.base.gestaoproducao.gestaomaterial.application.dto.MaterialDTO;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.UnidadeDeMedida;
import eapli.base.infrastructure.application.HasDTO;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Material implements AggregateRoot<CodigoInterno>, HasDTO<MaterialDTO> {

    @Version
    private Long version;

    @EmbeddedId
    private CodigoInterno codigoInterno;


    private UnidadeDeMedida unidadeDeMedida;
    private String descricao;
    private FichaTecnicaPDF fichaTecnicaPDF;



    @ManyToOne
    private Categoria categoria;


    protected Material() {
    }

    /**
     * @param descricaoMaterial Descricao do material
     * @param codigoInterno     Identificador com que queremos identificar o material
     * @param categoria         Categoria onde se encaixa o material
     * @param unidadeDeMedida   Unidade de medida usada para medir o material
     */
    public Material(String descricaoMaterial, CodigoInterno codigoInterno, Categoria categoria, UnidadeDeMedida unidadeDeMedida, FichaTecnicaPDF fichaTecnicaPDF) {
        if (!podeGerarMaterial(codigoInterno, unidadeDeMedida, descricaoMaterial, categoria, fichaTecnicaPDF))
            throw new IllegalArgumentException("Nenhum valor do produto pode ser nulo");
        this.codigoInterno = codigoInterno;
        this.unidadeDeMedida = unidadeDeMedida;
        this.descricao = descricaoMaterial;
        this.categoria = categoria;
        this.fichaTecnicaPDF = fichaTecnicaPDF;
    }

    public static String identityAttributeName() {
        return Reflection.retrieveAttributeName(Material.class, CodigoInterno.class);
    }

    private boolean podeGerarMaterial(CodigoInterno codigoInterno, UnidadeDeMedida unidadeDeMedida, String descricao, Categoria categoria, FichaTecnicaPDF fichaTecnicaPDF) {
        return codigoInterno != null && categoria != null && unidadeDeMedida != null && descricao != null && fichaTecnicaPDF != null;
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
    public CodigoInterno identity() {
        return this.codigoInterno;
    }

    @Override
    public String toString() {
        return "Material{" +
                "version=" + version +
                ", codigoInterno=" + codigoInterno +
                ", unidadeDeMedida=" + unidadeDeMedida +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                '}';
    }

    @Override
    public MaterialDTO toDTO() {
        return new MaterialDTO(codigoInterno.codigoInternoValor, descricao, unidadeDeMedida.unidadeDeMedidaValor, fichaTecnicaPDF.fichaTecnica, fichaTecnicaPDF.path);
    }
}