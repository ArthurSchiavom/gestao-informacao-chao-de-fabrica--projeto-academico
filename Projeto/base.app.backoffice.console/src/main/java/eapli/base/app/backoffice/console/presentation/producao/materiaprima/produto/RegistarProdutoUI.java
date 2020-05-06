package eapli.base.app.backoffice.console.presentation.producao.materiaprima.produto;

import eapli.base.app.backoffice.console.presentation.menu.OptionSelector;
import eapli.base.app.backoffice.console.presentation.utilities.UserInteractionControl;
import eapli.base.producao.materiaprima.produto.application.RegistarProdutosDeCsvController;
import eapli.base.producao.materiaprima.produto.application.RegistarProdutosDeFicheiroController;
import eapli.base.producao.materiaprima.produto.application.ResultadoImportacaoFicheiro;
import eapli.base.utilities.wrappers.Updateable;
import eapli.framework.presentation.console.AbstractUI;

import java.util.Map;
import java.util.Scanner;

public class RegistarProdutoUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        System.out.println("Introduza o caminho do ficheiro a carregar: ");
        Scanner scanner = new Scanner(System.in);
        String caminho = scanner.nextLine();

        System.out.println("\nCaso seja encontrado algum produto com código único já registado, pretende que a aplicação substitua o produto atual com o indicado?\n");
        OptionSelector optionSelector = new OptionSelector();
        final Updateable<Boolean> substituir = new Updateable<>();
        optionSelector.registerOption("Sim", () -> substituir.val = true);
        optionSelector.registerOption("Não", () -> substituir.val = false);
        optionSelector.show();

        RegistarProdutosDeFicheiroController controller = new RegistarProdutosDeCsvController();
        ResultadoImportacaoFicheiro resultado = controller.iniciar(caminho, substituir.val);

        System.out.println(construirMensagemResultado(resultado));
        UserInteractionControl.enterToContinue();

        return false;
    }

    public String construirMensagemResultado(ResultadoImportacaoFicheiro resultado) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nNúmero de produtos importados com sucesso: ").append(resultado.nSucessos)
                .append("\nNúmero de falhas: ").append(resultado.nFalhas);

        if (resultado.erros.size() > 0) {
            sb.append("\n\nAvisos:");
            for (Map.Entry<Integer, String> erro : resultado.erros.entrySet()) {
                sb.append("\nLinha nº").append(erro.getKey()).append(": ").append(erro.getValue());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String headline() {
        return "Registar Produtos";
    }
}
