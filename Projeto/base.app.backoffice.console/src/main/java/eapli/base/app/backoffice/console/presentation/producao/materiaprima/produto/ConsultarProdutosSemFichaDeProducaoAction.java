package eapli.base.app.backoffice.console.presentation.producao.materiaprima.produto;

import eapli.framework.actions.Action;

public class ConsultarProdutosSemFichaDeProducaoAction implements Action {
    @Override
    public boolean execute() {
        new ConsultarProdutosSemFichaDeProducaoUI().show();
        return false;
    }
}
