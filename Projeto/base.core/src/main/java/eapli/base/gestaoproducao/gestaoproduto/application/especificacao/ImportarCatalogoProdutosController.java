package eapli.base.gestaoproducao.gestaoproduto.application.especificacao;

public interface ImportarCatalogoProdutosController {
    ResultadoImportacaoCatalogoProdutos iniciar(String filePath, boolean substituirSeExistir);
}
