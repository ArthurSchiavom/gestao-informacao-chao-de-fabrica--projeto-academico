package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaquina.especificacao;

import eapli.framework.actions.Action;

public class EspecificarMaquinaAction implements Action{
    @Override
    public boolean execute() {
        return new EspecificarMaquinaUI().doShow();
    }
}
