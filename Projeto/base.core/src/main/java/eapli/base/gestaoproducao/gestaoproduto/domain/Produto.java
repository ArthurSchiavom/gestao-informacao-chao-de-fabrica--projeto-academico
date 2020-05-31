package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.gestaoproducao.medicao.UnidadeDeMedida;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.FichaDeProducaoDTO;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.ProdutoDTO;
import eapli.base.infrastructure.application.ConvertableToDTO;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Entity
public class Produto implements AggregateRoot<CodigoUnico>, ConvertableToDTO<ProdutoDTO> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @EmbeddedId
    @XmlAttribute
    public CodigoUnico codigoUnico;
    @Column(unique=true)
    @XmlElement
    public CodigoComercial codigoComercial;
    @OneToOne
    @XmlElement
    public FichaDeProducao fichaDeProducao;
    @XmlElement
    public CategoriaDeProduto categoriaDeProduto;
    @XmlElement
    public DescricaoBreve descricaoBreve;
    @XmlElement
    public DescricaoCompleta descricaoCompleta;
    @XmlElement
    public UnidadeDeMedida unidadeDeMedida;

    protected Produto() {
    }

    protected Produto(CodigoUnico codigoUnico, CategoriaDeProduto categoriaDeProduto, CodigoComercial codigoComercial, DescricaoBreve descricaoBreve, DescricaoCompleta descricaoCompleta, UnidadeDeMedida unidadeDeMedida) {
        if (!podeGerarProduto(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, unidadeDeMedida))
            throw new IllegalArgumentException("Nenhum valor do produto pode ser nulo");
        this.codigoUnico = codigoUnico;
        this.categoriaDeProduto = categoriaDeProduto;
        this.codigoComercial = codigoComercial;
        this.descricaoBreve = descricaoBreve;
        this.descricaoCompleta = descricaoCompleta;
        this.fichaDeProducao = null;
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public static String identityAttributeName() {
        return "codigoUnico";
    }

    public static boolean podeGerarProduto(CodigoUnico codigoUnico, CategoriaDeProduto categoriaDeProduto, CodigoComercial codigoComercial, DescricaoBreve descricaoBreve, DescricaoCompleta descricaoCompleta, UnidadeDeMedida unidadeDeMedida) {
        return codigoUnico != null && categoriaDeProduto != null && codigoComercial != null && descricaoBreve != null && descricaoCompleta != null && unidadeDeMedida != null;
    }

    public static Produto valueOf(CodigoUnico codigoUnico, CategoriaDeProduto categoriaDeProduto, CodigoComercial codigoComercial, DescricaoBreve descricaoBreve, DescricaoCompleta descricaoCompleta, UnidadeDeMedida unidadeDeMedida) {
        return new Produto(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, unidadeDeMedida);
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
    public CodigoUnico identity() {
        return this.codigoUnico;
    }

    @Override
    public ProdutoDTO toDTO() {
        FichaDeProducaoDTO fichaDeProducaoDTO = (fichaDeProducao == null) ? null : fichaDeProducao.toDTO();
        return new ProdutoDTO(categoriaDeProduto.categoriaValor, codigoComercial.codigoComercialValor,
                descricaoBreve.descricaoBreveValor, codigoUnico.codigoUnicoValor,
                descricaoCompleta.descricaoCompletaValor, fichaDeProducaoDTO,
                unidadeDeMedida.abreviatura);
    }
}
