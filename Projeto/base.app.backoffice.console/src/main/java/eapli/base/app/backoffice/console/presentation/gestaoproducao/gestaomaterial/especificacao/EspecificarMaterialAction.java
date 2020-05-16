package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaterial.especificacao;

import eapli.framework.actions.Action;


public class EspecificarMaterialAction implements Action {
    @Override
    public boolean execute() {
        return new EspecificarMaterialUI().doShow();
    }
}
