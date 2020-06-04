package eapli.base.infrastructure.bootstrapers;

import eapli.base.AppSettings;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ImportarCatalogoCsvProdutosController;
import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ImportarCatalogoProdutosController;
import eapli.framework.actions.Action;

public class ProdutoBootstrapper implements Action {

    private final ImportarCatalogoProdutosController controller = new ImportarCatalogoCsvProdutosController();

    @Override
    public boolean execute() {
        controller.importar(Application.settings().getProperty(AppSettings.BOOTSTRAP_PRODUTO), false);
        return true;
    }
}
