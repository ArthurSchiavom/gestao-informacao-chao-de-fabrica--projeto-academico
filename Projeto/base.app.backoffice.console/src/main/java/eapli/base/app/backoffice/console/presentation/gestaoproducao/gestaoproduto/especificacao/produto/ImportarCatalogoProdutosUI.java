package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.especificacao.produto;

import eapli.base.app.common.console.presentation.files.ApresentacaoResultadoImportacaoFicheiro;
import eapli.base.app.common.console.presentation.interaction.OptionSelector;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaoprodutos.application.registarprodutos.RegistarProdutosDeCsvController;
import eapli.base.gestaoproducao.gestaoprodutos.application.registarprodutos.RegistarProdutosDeFicheiroController;
import eapli.base.gestaoproducao.gestaoprodutos.application.registarprodutos.ResultadoImportacaoRegistoProdutos;
import eapli.base.utilities.wrappers.Updateable;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ImportarCatalogoProdutosUI extends AbstractUI {

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
        ResultadoImportacaoRegistoProdutos resultado = controller.iniciar(caminho, substituir.val);

        System.out.println("\n\n" + ApresentacaoResultadoImportacaoFicheiro.construirMensagemResultado(resultado) + "\n");
        UserInteractionFlow.enterToContinue();

        return false;
    }

    @Override
    public String headline() {
        return "Carregar Catálogo de Produtos.";
    }
}
