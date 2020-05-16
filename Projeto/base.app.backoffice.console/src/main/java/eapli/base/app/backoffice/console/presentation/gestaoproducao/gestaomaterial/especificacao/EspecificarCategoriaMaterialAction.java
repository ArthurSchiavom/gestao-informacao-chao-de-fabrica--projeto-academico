package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaterial.especificacao;

import eapli.framework.actions.Action;

public class EspecificarCategoriaMaterialAction implements Action {
    @Override
    public boolean execute() {
        return new EspecificarCategoriaMaterialUI().doShow();
    }
}
