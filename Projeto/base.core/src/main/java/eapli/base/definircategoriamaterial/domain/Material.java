package eapli.base.definircategoriamaterial.domain;

import eapli.base.materiaprima.domain.UnidadeDeMedida;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
public class Material implements AggregateRoot<CodigoInterno> {

    @Version
    private Long version;

    @EmbeddedId
    private CodigoInterno codigoInterno;
    private UnidadeDeMedida unidadeDeMedida;
    private String descricao;
    //private FichaTecnicaPDF fichaTecnicaPDF;

    @ManyToOne
    private Categoria categoria;

    protected Material(){
    }

    /**
     *
     * @param descricaoMaterial Descricao do material
     * @param nomeMaterial Nome do material
     * @param conteudoFichaTecnica Conteudo da ficha tecnica
     * @param codigoInterno Identificador com que queremos identificar o material
     * @param categoria Categoria onde se encaixa o material
     * @param unidadeDeMedida  Unidade de medida usada para medir o material
     */
    public Material(String descricaoMaterial, String nomeMaterial, String conteudoFichaTecnica, CodigoInterno codigoInterno, Categoria categoria, UnidadeDeMedida unidadeDeMedida)  {
        this.codigoInterno=codigoInterno;
        this.unidadeDeMedida=unidadeDeMedida;
        this.descricao=descricaoMaterial;
        this.categoria=categoria;
         if (!podeGerarMaterial(this.codigoInterno,this.unidadeDeMedida, this.descricao,this.categoria))
            throw new IllegalArgumentException("Nenhum valor do produto pode ser nulo");
      }


    public static String identityAttributeName(){
            return "codigoInterno";
    }

    private boolean podeGerarMaterial(CodigoInterno codigoInterno,UnidadeDeMedida unidadeDeMedida,String descricao,Categoria categoria){
        return codigoInterno != null && categoria !=null && unidadeDeMedida != null && descricao != null;
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
    public String toString() {
        return "Material{" +
                "version=" + version +
                ", codigoInterno=" + codigoInterno +
                ", unidadeDeMedida=" + unidadeDeMedida +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                '}';
    }

    public String descricao(){
        return this.descricao;
    }

}