package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.especificacao;

import eapli.base.app.common.console.presentation.files.ResultadoImportacaoFicheiroPresentationUtils;
import eapli.base.app.common.console.presentation.interaction.OptionSelector;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ImportarCatalogoCsvProdutosController;
import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ImportarCatalogoProdutosController;
import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ResultadoImportacaoLinhaALinha;
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

        ImportarCatalogoProdutosController controller = new ImportarCatalogoCsvProdutosController();
        ResultadoImportacaoLinhaALinha resultado = controller.importar(caminho, substituir.val);

        System.out.println("\n\n" + ResultadoImportacaoFicheiroPresentationUtils.construirMensagemResultado(resultado) + "\n");
        UserInteractionFlow.enterParaContinuar();

        return false;
    }

    @Override
    public String headline() {
        return "Carregar Catálogo de Produtos.";
    }
}
