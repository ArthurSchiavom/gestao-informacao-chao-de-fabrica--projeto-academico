package eapli.base.app.backoffice.console.presentation.produto;

import eapli.framework.actions.Action;

public class EspecificarFichaDeProducaoAction implements Action {
    @Override
    public boolean execute() {
        return new EspecificarFichaDeProducaoUI().show();
    }
}
