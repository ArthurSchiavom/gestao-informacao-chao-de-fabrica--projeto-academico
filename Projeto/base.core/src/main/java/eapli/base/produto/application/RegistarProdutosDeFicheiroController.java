package eapli.base.materiaprima.produto.application;

public interface RegistarProdutosDeFicheiroController {
    ResultadoImportacaoFicheiro iniciar(String filePath, boolean substituirSeExistir);
}
