package eapli.base.produto.application.registarprodutos;

public interface RegistarProdutosDeFicheiroController {
    ResultadoImportacaoRegistoProdutos iniciar(String filePath, boolean substituirSeExistir);
}
