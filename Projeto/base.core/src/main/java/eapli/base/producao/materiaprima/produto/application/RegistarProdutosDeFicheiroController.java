package eapli.base.producao.materiaprima.produto.application;

public interface RegistarProdutosDeFicheiroController {
    ResultadoImportacaoFicheiro iniciar(String filePath, boolean substituirSeExistir);
}
