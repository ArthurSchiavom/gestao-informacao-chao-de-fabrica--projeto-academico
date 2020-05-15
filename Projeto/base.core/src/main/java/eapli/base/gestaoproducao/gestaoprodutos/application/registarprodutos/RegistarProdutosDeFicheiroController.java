package eapli.base.gestaoproducao.gestaoprodutos.application.registarprodutos;

public interface RegistarProdutosDeFicheiroController {
    ResultadoImportacaoRegistoProdutos iniciar(String filePath, boolean substituirSeExistir);
}
