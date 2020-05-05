package eapli.base.app.backoffice.console.presentation.authz;

import eapli.base.definircategoriamaterial.application.DefinirCategoriaMaterialController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class RegistoCategoriaMaterialUI extends AbstractUI {

    private final DefinirCategoriaMaterialController theController = new DefinirCategoriaMaterialController();

    @Override
    protected boolean doShow() {
        /**
         * Uma categoria tem um código (alfanumérico 10 caracteres no máximo) e uma descrição.
         *
         * A semântica é dada pelo utilizador do sistema.
         */
        final String identifier = Console.readNonEmptyLine("Descricao",
                "Descricão não pode ser vazia");
        final String descricao = Console.readNonEmptyLine("Descricao",
                "Descricão não pode ser vazia");
        //TODO verificar o retorno aqui
        try {
            this.theController.registarCategoriaMaterial(identifier,descricao);
            return true;
        } catch (IllegalArgumentException ex) {
            System.out.println("Identificador inválido");
        }
        // continua no mesmo menu
        return false;
    }

    public String headline() {
        return "Registar Categoria Material";
    }

}
