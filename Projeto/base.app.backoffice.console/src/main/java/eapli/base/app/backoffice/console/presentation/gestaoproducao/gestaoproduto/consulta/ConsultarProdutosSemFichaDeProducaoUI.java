package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.consulta;

import eapli.base.app.common.console.presentation.formatter.ConsoleTables;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaoprodutos.application.ConsultarProdutosSemFichaDeProducaoController;
import eapli.base.gestaoproducao.gestaoprodutos.application.ProdutoDTO;
import eapli.framework.presentation.console.AbstractUI;

import java.util.List;

public class ConsultarProdutosSemFichaDeProducaoUI extends AbstractUI {
    ConsultarProdutosSemFichaDeProducaoController controller = new ConsultarProdutosSemFichaDeProducaoController();

    @Override
    protected boolean doShow() {
        List<ProdutoDTO> resultado = controller.iniciar();
        if (resultado.size() == 0) {
            System.out.println("Nenhum produto sem ficha de produção registado.");
            UserInteractionFlow.enterToContinue();
            return false;
        }

        String resultadoDisplay = ConsoleTables.tabelaDeProdutos(resultado);
        System.out.println(resultadoDisplay);
        UserInteractionFlow.enterToContinue();
        return false;
    }

    @Override
    public String headline() {
        return "Produtos Sem Ficha de Produção";
    }
}
