package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.consulta;

import eapli.framework.actions.Action;

public class ConsultarProdutosSemFichaDeProducaoAction implements Action {
    @Override
    public boolean execute() {
        new ConsultarProdutosSemFichaDeProducaoUI().show();
        return false;
    }
}
