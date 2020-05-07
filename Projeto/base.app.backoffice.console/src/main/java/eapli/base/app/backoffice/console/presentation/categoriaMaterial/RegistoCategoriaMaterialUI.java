package eapli.base.app.backoffice.console.presentation.categoriaMaterial;

import eapli.base.definircategoriamaterial.application.DefinirCategoriaMaterialController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import javax.persistence.RollbackException;

public class RegistoCategoriaMaterialUI extends AbstractUI {

    private final DefinirCategoriaMaterialController theController = new DefinirCategoriaMaterialController();

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
