package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.especificacao.produto;

import eapli.framework.actions.Action;

public class EspecificarProdutoAction implements Action {
    @Override
    public boolean execute() {
        return new EspecificarProdutoUI().show();
    }
}
