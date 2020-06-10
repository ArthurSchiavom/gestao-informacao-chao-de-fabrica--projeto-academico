package eapli.base.infrastructure.bootstrapers;

import eapli.base.gestaoproducao.gestaolinhasproducao.application.especificacao.EspecificarLinhaProducaoController;
import eapli.base.gestaoproducao.gestaomaquina.aplication.especificacao.EspecificarMaquinaController;
import eapli.framework.actions.Action;

public class MaquinaBootstrapper implements Action{

    EspecificarMaquinaController controller = new EspecificarMaquinaController();
    private final EspecificarLinhaProducaoController controllerLinha = new EspecificarLinhaProducaoController();

    @Override
    public boolean execute() {
        controllerLinha.registarLinhaProducao("Linha 5"); // para ter linhas para registar máquinas
        controllerLinha.registarLinhaProducao("Linha 6"); // para ter linhas para registar máquinas
        controller.registarMaquina(1,1,"DD4","Num 1234","DP-420","sony","HC-A",1,false);
        controller.registarMaquina(1,2,"T3","Num 1235","BJ-420","apple","HC-B",64931,false);
        controller.registarMaquina(2,2,"1235","Num 1000","K-9","samsung","HC-S",234,false);

        return true;
    }

}
