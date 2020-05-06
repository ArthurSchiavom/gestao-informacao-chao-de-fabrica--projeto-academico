package eapli.base.app.backoffice.console.presentation.producao.materiaprima.produto;

import eapli.framework.actions.Action;

public class RegistarProdutoAction implements Action {
    @Override
    public boolean execute() {
        return new RegistarProdutoUI().doShow();
    }
}
