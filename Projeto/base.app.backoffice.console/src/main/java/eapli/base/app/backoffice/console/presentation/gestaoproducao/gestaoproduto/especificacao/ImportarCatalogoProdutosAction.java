package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.especificacao;

import eapli.framework.actions.Action;

public class ImportarCatalogoProdutosAction implements Action {
    @Override
    public boolean execute() {
        return new ImportarCatalogoProdutosUI().show();
    }
}
