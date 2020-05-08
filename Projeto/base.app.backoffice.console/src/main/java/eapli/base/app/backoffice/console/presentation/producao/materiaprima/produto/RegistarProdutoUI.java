package eapli.base.app.backoffice.console.presentation.producao.materiaprima.produto;

import eapli.base.app.common.console.presentation.files.ResultadoImportacaoFicheiroPresentationUtils;
import eapli.base.app.backoffice.console.presentation.menu.OptionSelector;
import eapli.base.app.backoffice.console.presentation.utilities.UserInteractionControl;
import eapli.base.produto.application.RegistarProdutosDeCsvController;
import eapli.base.produto.application.RegistarProdutosDeFicheiroController;
import eapli.base.produto.application.ResultadoImportacaoFicheiro;
import eapli.base.utilities.wrappers.Updateable;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class RegistarProdutoUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        String caminho = Console.readLine("Introduza o caminho do ficheiro a carregar: ");

        System.out.println("\nCaso seja encontrado algum produto com código único já registado, pretende que a aplicação substitua o produto atual com o indicado?\n");
        OptionSelector optionSelector = new OptionSelector();
        final Updateable<Boolean> substituir = new Updateable<>();
        optionSelector.registerOption("Sim", () -> substituir.val = true);
        optionSelector.registerOption("Não", () -> substituir.val = false);
        optionSelector.show();

        RegistarProdutosDeFicheiroController controller = new RegistarProdutosDeCsvController();
        ResultadoImportacaoFicheiro resultado = controller.iniciar(caminho, substituir.val);

        System.out.println("\n\n" + ResultadoImportacaoFicheiroPresentationUtils.construirMensagemResultado(resultado) + "\n");
        UserInteractionControl.enterToContinue();

        return false;
    }

    @Override
    public String headline() {
        return "Registar Produtos";
    }
}
