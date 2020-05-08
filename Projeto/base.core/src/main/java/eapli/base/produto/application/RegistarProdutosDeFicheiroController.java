package eapli.base.produto.application;

public interface RegistarProdutosDeFicheiroController {
    ResultadoImportacaoFicheiro iniciar(String filePath, boolean substituirSeExistir);
}
