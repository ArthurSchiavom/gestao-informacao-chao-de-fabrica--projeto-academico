package eapli.base.producao.materiaprima.produto.application;

import eapli.base.infrastructure.domain.IllegalDomainValue;
import eapli.base.producao.materiaprima.domain.UnidadeDeMedida;
import eapli.base.producao.materiaprima.produto.domain.*;

/**
 * Facilita a atribuição gradual de valores, podendo então tratar os possíveis erros para cada atributo individualmente.
 */
public class ProdutoBuilder {
    private CodigoUnico codigoUnico = null;
    private CategoriaDeProduto categoriaDeProduto = null;
    private CodigoComercial codigoComercial = null;
    private DescricaoBreve descricaoBreve = null;
    private DescricaoCompleta descricaoCompleta = null;
    private UnidadeDeMedida unidadeDeMedida = null;

    public ProdutoBuilder() {
    }

    public ProdutoBuilder setCodigoUnico(String codigoUnico) throws IllegalDomainValue {
        this.codigoUnico = CodigoUnico.valueOf(codigoUnico);
        return this;
    }

    public ProdutoBuilder setCategoriaDeProduto(String categoriaDeProduto) {
        this.categoriaDeProduto = CategoriaDeProduto.valueOf(categoriaDeProduto);
        return this;
    }

    public ProdutoBuilder setCodigoComercial(String codigoComercial) throws IllegalDomainValue {
        this.codigoComercial = CodigoComercial.valueOf(codigoComercial);
        return this;
    }

    public ProdutoBuilder setDescricaoBreve(String descricaoBreve) throws IllegalDomainValue {
        this.descricaoBreve = DescricaoBreve.valueOf(descricaoBreve);
        return this;
    }

    public ProdutoBuilder setDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = DescricaoCompleta.valueOf(descricaoCompleta);
        return this;
    }

    public ProdutoBuilder setUnidadeDeMedida(String unidadeDeMedida) {
        this.unidadeDeMedida = UnidadeDeMedida.valueOf(unidadeDeMedida);
        return this;
    }

    public boolean isReady() {
        return Produto.podeGerarProduto(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, unidadeDeMedida);
    }

    public Produto build() {
        return Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, unidadeDeMedida);
    }
}
