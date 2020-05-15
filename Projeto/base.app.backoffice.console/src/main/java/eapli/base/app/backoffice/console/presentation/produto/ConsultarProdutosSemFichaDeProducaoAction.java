package eapli.base.app.backoffice.console.presentation.produto;

import eapli.framework.actions.Action;

public class ConsultarProdutosSemFichaDeProducaoAction implements Action {
    @Override
    public boolean execute() {
        new ConsultarProdutosSemFichaDeProducaoUI().show();
        return false;
    }
}
