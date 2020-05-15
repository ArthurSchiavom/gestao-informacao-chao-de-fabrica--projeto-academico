package eapli.base.infrastructure.bootstrapers;

import eapli.base.AppSettings;
import eapli.base.Application;
import eapli.base.produto.application.registarprodutos.RegistarProdutosDeCsvController;
import eapli.base.produto.application.registarprodutos.RegistarProdutosDeFicheiroController;
import eapli.framework.actions.Action;

public class ProdutoBootstrapper implements Action {

    private final RegistarProdutosDeFicheiroController controller = new RegistarProdutosDeCsvController();

    @Override
    public boolean execute() {
        controller.iniciar(Application.settings().getProperty(AppSettings.BOOTSTRAP_PRODUTO), false);
        return true;
    }
}
