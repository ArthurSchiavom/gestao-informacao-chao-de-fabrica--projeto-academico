package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaquina.especificacao;

import eapli.framework.actions.Action;

public class EspecificarFicheiroConfiguracaoAction implements Action {
    @Override
    public boolean execute() {
        return new EspecificarFicheiroConfiguracaoUI().doShow();
    }
}
