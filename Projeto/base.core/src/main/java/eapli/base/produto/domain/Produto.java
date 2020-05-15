package eapli.base.produto.domain;

import eapli.base.infrastructure.application.HasDTO;
import eapli.base.materiaprima.domain.UnidadeDeMedida;
import eapli.base.produto.application.FichaDeProducaoDTO;
import eapli.base.produto.application.ProdutoDTO;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
public class Produto implements AggregateRoot<CodigoUnico>, HasDTO<ProdutoDTO> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @EmbeddedId
    public CodigoUnico codigoUnico;

    @Column(unique=true)
    public CodigoComercial codigoComercial;
    @OneToOne
    public FichaDeProducao fichaDeProducao;
    public CategoriaDeProduto categoriaDeProduto;
    public DescricaoBreve descricaoBreve;
    public DescricaoCompleta descricaoCompleta;
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
        return Reflection.retrieveAttributeName(Produto.class, CodigoUnico.class);
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
                unidadeDeMedida.unidadeDeMedidaValor);
    }
}
