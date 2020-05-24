package eapli.base.gestaoproducao.gestaoproduto.application.especificacao;

public interface ImportarCatalogoProdutosController {
    ResultadoImportacaoLinhaALinha iniciar(String filePath, boolean substituirSeExistir);
}
