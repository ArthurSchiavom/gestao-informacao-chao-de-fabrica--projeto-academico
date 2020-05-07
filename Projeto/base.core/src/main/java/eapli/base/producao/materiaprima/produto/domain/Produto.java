package eapli.base.producao.materiaprima.produto.domain;

import eapli.base.producao.materiaprima.domain.UnidadeDeMedida;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class Produto implements AggregateRoot<CodigoUnico> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @EmbeddedId
    public final CodigoUnico codigoUnico;

    public static String identityAttributeName() {
        return Reflection.retrieveAttributeName(Produto.class, CodigoUnico.class);
    }

    public final CategoriaDeProduto categoriaDeProduto;
    public final CodigoComercial codigoComercial;
    public final DescricaoBreve descricaoBreve;
    public final DescricaoCompleta descricaoCompleta;
    public final FichaDeProducao fichaDeProducao;
    public final UnidadeDeMedida unidadeDeMedida;

    protected Produto() {
        this.codigoUnico = null;
        this.categoriaDeProduto = null;
        this.codigoComercial = null;
        this.descricaoBreve = null;
        this.descricaoCompleta = null;
        this.fichaDeProducao = null;
        this.unidadeDeMedida = null;
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
}
