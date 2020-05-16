package eapli.base.infrastructure.bootstrapers;

import eapli.base.gestaoproducao.gestaomaterial.application.EspecificarCategoriaMaterialController;
import eapli.framework.actions.Action;

public class CategoriaBootstrapper implements Action{

    EspecificarCategoriaMaterialController controller = new EspecificarCategoriaMaterialController();


    @Override
    public boolean execute() {
        controller.registarCategoriaMaterial("ID-123","Metal");
        controller.registarCategoriaMaterial("ID-122","Madeira");
        controller.registarCategoriaMaterial("ID-121","Plástico");

        return true;
    }
}
