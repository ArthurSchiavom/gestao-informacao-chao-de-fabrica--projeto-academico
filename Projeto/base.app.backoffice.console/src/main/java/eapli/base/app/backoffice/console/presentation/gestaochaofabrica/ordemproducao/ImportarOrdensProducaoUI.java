package eapli.base.app.backoffice.console.presentation.gestaochaofabrica.ordemproducao;

import eapli.base.app.common.console.presentation.files.ResultadoImportacaoFicheiroPresentationUtils;
import eapli.base.app.common.console.presentation.interaction.OptionSelector;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ResultadoImportacaoLinhaALinha;
import eapli.base.gestaoproducao.ordemProducao.application.ImportarOrdensProducaoController;
import eapli.base.utilities.wrappers.Updateable;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ImportarOrdensProducaoUI extends AbstractUI {

    ImportarOrdensProducaoController controller = new ImportarOrdensProducaoController();

    @Override
    protected boolean doShow() {

        String caminho = Console.readLine("Introduza o caminho do ficheiro a carregar: ");

        System.out.println("\nCaso seja encontrado alguma ordem de produção com identificador já registado, pretende " +
                "que a aplicação substitua a ordem de produção atual com o indicado?\n");
        OptionSelector optionSelector = new OptionSelector();
        final Updateable<Boolean> substituir = new Updateable<>();
        optionSelector.registerOption("Sim", () -> substituir.val = true);
        optionSelector.registerOption("Não", () -> substituir.val = false);
        optionSelector.show();

        ResultadoImportacaoLinhaALinha resultado = controller.importarOrdensProducao(caminho, substituir.val);

        System.out.println("\n\n" + ResultadoImportacaoFicheiroPresentationUtils.construirMensagemResultado(resultado) + "\n");
        UserInteractionFlow.enterToContinue();

        return false;
    }

    @Override
    public String headline() {
        return "Especificar ordem produção";
    }
}
