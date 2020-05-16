package eapli.base.gestaoproducao.gestaoproduto.application;

import eapli.base.gestaoproducao.medicao.UnidadeDeMedida;
import eapli.base.gestaoproducao.gestaoproduto.domain.*;
import eapli.base.infrastructure.domain.IllegalDomainValueException;

/**
 * Facilita a leitura do código no controller e a atribuição gradual de valores, podendo então tratar os possíveis erros para cada atributo individualmente.
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

    public ProdutoBuilder setCodigoUnico(String codigoUnico) throws IllegalDomainValueException {
        this.codigoUnico = CodigoUnico.valueOf(codigoUnico);
        return this;
    }

    public ProdutoBuilder setCategoriaDeProduto(String categoriaDeProduto) {
        this.categoriaDeProduto = CategoriaDeProduto.valueOf(categoriaDeProduto);
        return this;
    }

    public ProdutoBuilder setCodigoComercial(String codigoComercial) throws IllegalDomainValueException {
        this.codigoComercial = CodigoComercial.valueOf(codigoComercial);
        return this;
    }

    public ProdutoBuilder setDescricaoBreve(String descricaoBreve) throws IllegalDomainValueException {
        this.descricaoBreve = DescricaoBreve.valueOf(descricaoBreve);
        return this;
    }

    public ProdutoBuilder setDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = DescricaoCompleta.valueOf(descricaoCompleta);
        return this;
    }

    public ProdutoBuilder setUnidadeDeMedida(String unidadeDeMedida) throws IllegalDomainValueException {
        this.unidadeDeMedida = UnidadeDeMedida.actualValueOf(unidadeDeMedida);
        return this;
    }

    public boolean isReady() {
        return Produto.podeGerarProduto(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, unidadeDeMedida);
    }

    public Produto build() {
        return Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, unidadeDeMedida);
    }
}
