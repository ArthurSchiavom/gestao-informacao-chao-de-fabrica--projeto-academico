package eapli.base.produto.application;

public interface RegistarProdutosDeFicheiroController {
    ResultadoImportacaoRegistoProdutos iniciar(String filePath, boolean substituirSeExistir);
}
