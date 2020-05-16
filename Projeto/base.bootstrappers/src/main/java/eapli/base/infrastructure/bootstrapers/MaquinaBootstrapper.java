package eapli.base.infrastructure.bootstrapers;

import eapli.base.gestaoproducao.gestaolinhasproducao.application.AddLinhaProducaoController;
import eapli.base.gestaoproducao.gestaomaquina.aplication.RegistarMaquinaController;
import eapli.framework.actions.Action;

public class MaquinaBootstrapper implements Action{

    RegistarMaquinaController controller = new RegistarMaquinaController();
    private final AddLinhaProducaoController controllerLinha = new AddLinhaProducaoController();

    @Override
    public boolean execute() {
        controllerLinha.registarLinhaProducao("Linha 5"); // para ter linhas para registar máquinas
        controllerLinha.registarLinhaProducao("Linha 6"); // para ter linhas para registar máquinas
        controller.registarMaquina(1,1,"123","Num 1234","DP-420","sony","HC-A","UDP");
        controller.registarMaquina(1,2,"123","Num 1235","BJ-420","apple","HC-B","TCP");
        controller.registarMaquina(2,2,"123","Num 1000","K-9","samsung","HC-S","TCP");

        return true;
    }

}
