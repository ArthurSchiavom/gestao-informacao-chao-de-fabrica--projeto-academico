package eapli.base.app.backoffice.console.presentation.categoriaMaterial;

import eapli.framework.actions.Action;

public class RegistoCategoriaMaterialAction implements Action {
    @Override
    public boolean execute() {
        return new RegistoCategoriaMaterialUI().doShow();
    }
}
