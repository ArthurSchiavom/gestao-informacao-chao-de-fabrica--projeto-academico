package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaterial.especificacao;

import eapli.base.gestaoproducao.gestaomaterial.application.EspecificarCategoriaMaterialController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import javax.persistence.RollbackException;

public class EspecificarCategoriaMaterialUI extends AbstractUI {

    private final EspecificarCategoriaMaterialController theController = new EspecificarCategoriaMaterialController();

    @Override
    protected boolean doShow() {
        /**
         * Uma categoria tem um código (alfanumérico 10 caracteres no máximo) e uma descrição.
         *
         * A semântica é dada pelo utilizador do sistema.
         */
        final String identifier = Console.readNonEmptyLine("Identificador ",
                "Identificador não pode ser vazio");
        final String descricao = Console.readNonEmptyLine("Descricao ",
                "Descricão não pode ser vazia");
        //TODO verificar o retorno aqui
        try {
            this.theController.registarCategoriaMaterial(identifier,descricao);
            return true;
        } catch (IllegalArgumentException| RollbackException ex) {
            System.out.println("Identificador inválido ou já registado");
        }
        // continua no mesmo menu
        return false;
    }

    public String headline() {
        return "Registar Categoria Material";
    }

}
