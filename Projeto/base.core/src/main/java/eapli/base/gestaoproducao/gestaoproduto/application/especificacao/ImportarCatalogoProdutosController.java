package eapli.base.gestaoproducao.gestaoproduto.application.especificacao;

public interface ImportarCatalogoProdutosController {
    ResultadoImportacaoLinhaALinha importar(String filePath, boolean substituirSeExistir);
}
