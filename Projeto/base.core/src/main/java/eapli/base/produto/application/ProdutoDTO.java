package eapli.base.produto.application;

public class ProdutoDTO {
    public final String categoriaDeProduto;
    public final String codigoComercial;
    public final String codigoUnico;
    public final String descricaoBreve;
    public final String descricaoCompleta;
    public final FichaDeProducaoDTO fichaDeProducao;
    public final String unidadeDeMedida;

    public ProdutoDTO(String categoriaDeProduto, String codigoComercial, String descricaoBreve, String codigoUnico, String descricaoCompleta, FichaDeProducaoDTO fichaDeProducao, String unidadeDeMedida) {
        this.categoriaDeProduto = categoriaDeProduto;
        this.codigoUnico = codigoUnico;
        this.codigoComercial = codigoComercial;
        this.descricaoBreve = descricaoBreve;
        this.descricaoCompleta = descricaoCompleta;
        this.fichaDeProducao = fichaDeProducao;
        this.unidadeDeMedida = unidadeDeMedida;
    }
}
