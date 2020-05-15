package eapli.base.infrastructure.bootstrapers;

import eapli.base.gestaoproducao.gestaomateriais.application.RegistarCategoriaMaterialController;
import eapli.framework.actions.Action;

public class CategoriaBootstrapper implements Action{

    RegistarCategoriaMaterialController controller = new RegistarCategoriaMaterialController();


    @Override
    public boolean execute() {
        controller.registarCategoriaMaterial("ID-123","Metal");
        controller.registarCategoriaMaterial("ID-122","Madeira");
        controller.registarCategoriaMaterial("ID-121","Plástico");

        return true;
    }
}
